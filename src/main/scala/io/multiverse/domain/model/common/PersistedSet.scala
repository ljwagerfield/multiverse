package io.multiverse.domain.model.common

import io.multiverse.domain.stores.PersistenceStore

/**
 * Persisted collection used to apply state between aggregates.
 * @tparam E Event type.
 */
trait PersistedSet[E] {
  /**
   * Identifies where this model is hosted.
   */
  val store: PersistenceStore

  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to apply.
   */
  def apply(changes: List[E])

  /**
   * Removes the model from the associated store.
   */
  def destroy()
}
