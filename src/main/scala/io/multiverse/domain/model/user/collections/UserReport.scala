package io.multiverse.domain.model.user.collections

import io.multiverse.domain.stores.{PersistenceStore, GoogleAppEngineStore, FileSystemStore}
import io.multiverse.domain.model.common.values.Email

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
    store match {
      case store: GoogleAppEngineStore => new GAEUserReport(store)
      case store: FileSystemStore => new FSUserReport(store)
      case _ => sys.error("Incompatible store.")
    }
  }
}
