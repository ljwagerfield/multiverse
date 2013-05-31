package io.multiverse.domain.collections.instance

import io.multiverse.domain.aggregates.instance.InstanceEvent
import io.multiverse.domain.collections.common.{PersistedSet}
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
   * Creates an instance model in the provided store. Method has not effect if the model already exists.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def createIn(store: PersistenceStore):InstanceSet = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store.
    if (store.isInstanceOf[GoogleAppEngineStore]) {
      GAEInstanceSet.createIn(store.asInstanceOf[GoogleAppEngineStore])
    }
    else if (store.isInstanceOf[FileSystemStore]) {
      FSInstanceSet.createIn(store.asInstanceOf[FileSystemStore])
    }
    else {
      sys.error("Incompatible store.")
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
    if (store.isInstanceOf[GoogleAppEngineStore]) {
      new GAEInstanceSet(store.asInstanceOf[GoogleAppEngineStore])
    }
    else if (store.isInstanceOf[FileSystemStore]) {
      new FSInstanceSet(store.asInstanceOf[FileSystemStore])
    }
    else {
      sys.error("Incompatible store.")
    }
  }
}
