package io.multiverse.domain.collections.user

import io.multiverse.domain.aggregates.common.Email
import io.multiverse.domain.stores.{PersistenceStore, GoogleAppEngineStore, FileSystemStore}

/**
 * User interface.
 */
abstract class UserReport {
  /**
   * Identifies where this model is hosted.
   */
  val store: PersistenceStore

  /**
   * Tests the availability of the provided email.
   * @param email Email to check.
   * @return True if email taken. False if available.
   */
  def isEmailTaken(email: Email): Boolean
}

/**
 * User factory.
 */
object UserReport {
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
  def getFor(store: PersistenceStore): UserReport = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store
    if (store.isInstanceOf[GoogleAppEngineStore]) {
      new GAEUserReport(store.asInstanceOf[GoogleAppEngineStore])
    }
    else if (store.isInstanceOf[FileSystemStore]) {
      new FSUserReport(store.asInstanceOf[FileSystemStore])
    }
    else {
      sys.error("Incompatible store.")
    }
  }
}
