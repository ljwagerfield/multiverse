package io.multiverse.domain.collections.instance

import io.multiverse.domain.aggregates.instance.InstanceEvent
import io.multiverse.domain.stores.GoogleAppEngineStore

/**
 * Instance model hosted in a Google App Engine store.
 */
class GAEInstanceSet(val store: GoogleAppEngineStore) extends InstanceSet {
  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to apply.
   */
  def apply(changes: List[InstanceEvent]) {
    sys.error("Not implemented.")
  }

  /**
   * Removes the model from the associated store.
   */
  def destroy() {
    sys.error("Not implemented.")
  }
}

/**
 * Instance model factory for the Google App Engine.
 */
object GAEInstanceSet {
  /**
   * Creates an instance model in the provided store. Method has not effect if the model already exists.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def createIn(store: GoogleAppEngineStore): GAEInstanceSet = {
    // Create appropriate tables in store.
    new GAEInstanceSet(store)
  }
}
