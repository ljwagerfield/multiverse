package io.multiverse.domain.model.user.collections

import io.multiverse.domain.model.common.VolatilePersistedSet
import io.multiverse.domain.model.user.{UserConflict, UserEvent}
import io.multiverse.domain.stores.{PersistenceStore, GoogleAppEngineStore, FileSystemStore}

/**
 * Persisted collection used to apply and verify state between user aggregates.
 */
abstract class UserSet extends VolatilePersistedSet[UserEvent, UserConflict]

/**
 * User set factory.
 */
object UserSet {
  /**
   * Types of store supported by this model.
   * @return Supported store types.
   */
  def supportedStores: List[Class[_ <: PersistenceStore]] = List(classOf[GoogleAppEngineStore], classOf[FileSystemStore])

  /**
   * Creates an user model in the provided store. Method has not effect if the model already exists.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def createIn(store: PersistenceStore): UserSet = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store.
    store match {
      case store: GoogleAppEngineStore => GAEUserSet.createIn(store)
      case store: FileSystemStore => FSUserSet.createIn(store)
      case _ => sys.error("Incompatible store.")
    }
  }

  /**
   * Gets the user model from the provided store.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def getFrom(store: PersistenceStore): UserSet = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store.
    store match {
      case store: GoogleAppEngineStore => new GAEUserSet(store)
      case store: FileSystemStore => new FSUserSet(store)
      case _ => sys.error("Incompatible store.")
    }
  }
}
