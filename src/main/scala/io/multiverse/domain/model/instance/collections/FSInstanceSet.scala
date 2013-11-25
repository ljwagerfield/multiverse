package io.multiverse.domain.model.instance.collections

import io.multiverse.application.SimpleIO.stringContent
import io.multiverse.domain.model.instance.{UserSignedOut, UserSignedIn, InstanceEvent}
import io.multiverse.domain.stores.FileSystemStore
import java.io.File
import scala.io.Source

/**
 * Instance model hosted on the file system. This model is intended for development use only and is not
 * production-ready. Failures may result in model corruption.
 */
class FSInstanceSet(val store: FileSystemStore) extends InstanceSet with FSInstancePaths {
  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to apply.
   */
  def apply(changes: List[InstanceEvent]) {
    val usersPath = signedInUsersPath
    val instancesPath = signedInInstancesPath
    val idSize = 36

    for (change <- changes) {
      val instance = change.instanceId.value.toString
      change match {
        case signedIn: UserSignedIn =>
          val user = signedIn.userId.value.toString

          // Create signed-in user file containing instance ID.
          instance saveTo usersPath + user

          // Create signed-in instance file containing user ID.
          user saveTo instancesPath + instance
        case _: UserSignedOut =>
          // Read signed-in user ID from instance file.
          val instanceFile = new File(instancesPath + instance)
          val user = Source.fromFile(instanceFile.getAbsolutePath).take(idSize).mkString

          // Delete signed-in instance file.
          instanceFile.delete()

          // Delete signed-in user file.
          new File(usersPath + user).delete()
        case _ =>
      }
    }
  }

  /**
   * Removes the model from the associated store.
   */
  def destroy() {
    new File(signedInInstancesPath).delete()
    new File(signedInUsersPath).delete()
  }
}

/**
 * Instance model factory for the Google App Engine.
 */
object FSInstanceSet {
  /**
   * Creates an instance model in the provided store. Method has not effect if the model already exists.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def createIn(store:FileSystemStore):FSInstanceSet = {
    // Build then return the model.
    val model = new FSInstanceSet(store)
    new File(model.signedInUsersPath).mkdir()
    new File(model.signedInInstancesPath).mkdir()
    model
  }
}
