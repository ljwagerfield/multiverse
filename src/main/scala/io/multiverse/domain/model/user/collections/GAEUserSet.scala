package io.multiverse.domain.model.user.collections

import io.multiverse.domain.model.user.{UserConflict, UserEvent}
import io.multiverse.domain.stores.GoogleAppEngineStore

/**
 * User model hosted on the Google App Engine.
 */
class GAEUserSet(val store: GoogleAppEngineStore) extends UserSet {
  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to apply.
   */
  def apply(changes: List[UserEvent]) {
    sys.error("Not implemented.")
  }

  /**
   * Removes the model from the associated store.
   */
  def destroy() {
    sys.error("Not implemented.")
  }

  /**
   * Identifies any conflicts resulting from previous attempts at denormalizing events from given sequences.
   * @return All unresolved conflicts for this model up-to the last denormalization for the associate store.
   */
  def conflicts: List[UserConflict] = {
    sys.error("Not implemented.")
  }
}

/**
 * User model factory for the Google App Engine.
 */
object GAEUserSet {
  /**
   * Creates a new user model in the provided store. Method has no effect if the model already exists.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def createIn(store: GoogleAppEngineStore): GAEUserSet = {
    // Configure necessary prerequisites in store.
    new GAEUserSet(store)
  }
}
