package io.multiverse.domain.model.instance.collections

import io.multiverse.domain.model.common.PersistedSet
import io.multiverse.domain.model.instance.InstanceEvent
import io.multiverse.domain.stores.{PersistenceStore, GoogleAppEngineStore, FileSystemStore}

/**
 * Persisted collection used to apply state between instance aggregates.
 */
abstract class InstanceSet extends PersistedSet[InstanceEvent]

/**
 * Instance model factory.
 */
object InstanceSet {
  /**
   * Types of store supported by this model.
   * @return Supported store types.
   */
  def supportedStores: List[Class[_ <: PersistenceStore]] = List(classOf[GoogleAppEngineStore], classOf[FileSystemStore])

  /**
   * Creates an instance model in the provided store. Method has no effect if the model already exists.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def createIn(store: PersistenceStore):InstanceSet = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store.
    store match {
      case store: GoogleAppEngineStore => GAEInstanceSet.createIn(store)
      case store: FileSystemStore => FSInstanceSet.createIn(store)
      case _ => sys.error("Incompatible store.")
    }
  }

  /**
   * Gets the instance model from the provided store.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def getFrom(store:PersistenceStore):InstanceSet = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store.
    store match {
      case store: GoogleAppEngineStore => new GAEInstanceSet(store)
      case store: FileSystemStore => new FSInstanceSet(store)
      case _ => sys.error("Incompatible store.")
    }
  }
}
