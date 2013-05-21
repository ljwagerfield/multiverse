package io.multiverse.application.persistence

/**
 * Persistence model used for querying data across multiple aggregates.
 * @tparam E Event type.
 */
trait PersistenceModel[E] {
  /**
   * Identifies where this model is hosted.
   * @return Model location.
   */
  def store: PersistenceStore

  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to denormalize.
   */
  def denormalize(changes: List[E])

  /**
   * Removes the model from the associated store.
   */
  def destroy()
}
