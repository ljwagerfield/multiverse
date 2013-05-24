package io.multiverse.application.persistence

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.UserId

/**
 * Instance query interface.
 */
abstract class InstanceQueries {
  /**
   * Identifies where this model is hosted.
   * @return Model location.
   */
  def store: PersistenceStore

  /**
   * Gets all users who are currently signed-in.
   * @return Signed-in users.
   */
  def getSignedInUsers: List[UserId]

  /**
   * Gets the count of users who are currently signed-in.
   * @return Number of signed-in users.
   */
  def getSignedInUserCount: Int

  /**
   * Gets the instance the specified user is operating within.
   * @param user Signed-in user.
   * @return Instance the specified user is signed-into.
   */
  def getUserInstance(user: UserId): InstanceId
}

/**
 * Instance query factory.
 */
object InstanceQueries {
  /**
   * Types of store supported by this query interface.
   * @return Supported store types.
   */
  def supportedStores: List[Class[_ <: PersistenceStore]] = List(classOf[GoogleAppEngineStore], classOf[FileSystemStore])

  /**
   * Get the instance queries for the provided store.
   * @param store Persistence store.
   * @return Queries for the given store.
   */
  def getFor(store:PersistenceStore): InstanceQueries = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store
    if (store.isInstanceOf[GoogleAppEngineStore]) {
      val googleAppEngineStore = store.asInstanceOf[GoogleAppEngineStore]
      new GAEInstanceQueries {
        override def store = googleAppEngineStore
      }
    }
    else if (store.isInstanceOf[FileSystemStore]) {
      val fileSystemStore = store.asInstanceOf[FileSystemStore]
      new FSInstanceQueries {
        override def store = fileSystemStore
      }
    }
    else {
      sys.error("Incompatible store.")
    }
  }
}
