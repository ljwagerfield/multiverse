package io.multiverse.application.persistence

import io.multiverse.application.SimpleIO.stringContent
import io.multiverse.domain.model.instance.{UserSignedOut, UserSignedIn, InstanceEvent}
import java.io.File
import scala.io.Source

/**
 * Instance model hosted on the file system.
 */
abstract class FSInstanceModel extends InstanceModel with FSInstancePaths {
  /**
   * Identifies where this model is hosted.
   * @return Model location.
   */
  def store: FileSystemStore

  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to denormalize.
   */
  def denormalize(changes: List[InstanceEvent]) {
    val usersPath = signedInUsersPath
    val instancesPath = signedInInstancesPath
    val idSize = 36

    for (change <- changes) {
      val changeType = change.getClass
      val instance = change.instanceId.value.toString

      if (changeType == classOf[UserSignedIn]) {
        val user = change.asInstanceOf[UserSignedIn].speciesId.value.toString

        // Create signed-in user file containing instance ID.
        instance saveTo usersPath + user

        // Create signed-in instance file containing user ID.
        user saveTo instancesPath + instance
      }
      else if (changeType == classOf[UserSignedOut]) {
        // Read signed-in user ID from instance file.
        val instanceFile = new File(instancesPath + instance)
        val user = Source.fromFile(instanceFile.getAbsolutePath).take(idSize).mkString

        // Delete signed-in instance file.
        instanceFile.delete()

        // Delete signed-in user file.
        new File(usersPath + user).delete()
      }
    }
  }

  /**
   * Removes the model from the associated store.
   */
  def destroy() {
    new File(signedInInstancesPath).delete
    new File(signedInUsersPath).delete
  }
}

/**
 * Instance model factory for the Google App Engine.
 */
object FSInstanceModel {
  /**
   * Creates an instance model in the provided store. Method has not effect if the model already exists.
   * @param fileSystemStore Persistence store.
   * @return Model for the given store.
   */
  def createIn(fileSystemStore:FileSystemStore):FSInstanceModel = {
    val model = new FSInstanceModel {
      override def store = fileSystemStore

      /**
       * Builds the model.
       */
      def build() {
        new File(signedInUsersPath).mkdir()
        new File(signedInInstancesPath).mkdir()
      }
    }

    model.build()
    model
  }
}
