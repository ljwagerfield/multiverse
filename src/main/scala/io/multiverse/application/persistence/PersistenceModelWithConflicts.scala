package io.multiverse.application.persistence

/**
 * Persistence model used for querying data across multiple aggregates. Events may conflict.
 * @tparam E Event type.
 * @tparam C Conflict type.
 */
trait PersistenceModelWithConflicts[E, C] extends PersistenceModel[E] {
  /**
   * Identifies any conflicts resulting from previous attempts at denormalizing events from given sequences.
   * @return All unresolved conflicts for this model up-to the last denormalization for the associate store.
   */
  def conflicts: List[C]
}
