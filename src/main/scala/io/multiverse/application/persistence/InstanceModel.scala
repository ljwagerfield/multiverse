package io.multiverse.application.persistence

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Persistence model used for querying data across multiple instances.
 */
abstract class InstanceModel extends PersistenceModel[InstanceEvent]

/**
 * Instance model factory.
 */
object InstanceModel {
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
  def createIn(store: PersistenceStore):InstanceModel = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store
    if (store.isInstanceOf[GoogleAppEngineStore]) {
      GAEInstanceModel.createIn(store.asInstanceOf[GoogleAppEngineStore])
    }
    else if (store.isInstanceOf[FileSystemStore]) {
      FSInstanceModel.createIn(store.asInstanceOf[FileSystemStore])
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
  def getFrom(store:PersistenceStore):InstanceModel = {
    require(supportedStores.contains(store.getClass))

    // Determine correct implementation from store
    if (store.isInstanceOf[GoogleAppEngineStore]) {
      val googleAppEngineStore = store.asInstanceOf[GoogleAppEngineStore]
      new GAEInstanceModel {
        override def store = googleAppEngineStore
      }
    }
    else if (store.isInstanceOf[FileSystemStore]) {
      val fileSystemStore = store.asInstanceOf[FileSystemStore]
      new FSInstanceModel {
        override def store = fileSystemStore
      }
    }
    else {
      sys.error("Incompatible store.")
    }
  }
}
