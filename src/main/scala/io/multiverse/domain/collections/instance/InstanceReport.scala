package io.multiverse.domain.collections.instance

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.user.UserId
import io.multiverse.domain.stores.{PersistenceStore, GoogleAppEngineStore, FileSystemStore}

/**
 * Instance query interface.
 */
abstract class InstanceReport {
  /**
   * Identifies where this model is hosted.
   */
  val store: PersistenceStore

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
object InstanceReport {
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
  def getFor(store:PersistenceStore): InstanceReport = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store
    if (store.isInstanceOf[GoogleAppEngineStore]) {
      new GAEInstanceReport(store.asInstanceOf[GoogleAppEngineStore])
    }
    else if (store.isInstanceOf[FileSystemStore]) {
      new FSInstanceReport(store.asInstanceOf[FileSystemStore])
    }
    else {
      sys.error("Incompatible store.")
    }
  }
}
