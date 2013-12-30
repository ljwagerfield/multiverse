package io.multiverse.domain.model.common

/**
 * Persisted collection used to apply and verify state between aggregates with potential conflicts.
 * @tparam E Event type.
 * @tparam C Conflict type.
 */
trait VolatilePersistedSet[-E, +C] extends PersistedSet[E] {

  /**
   * Identifies any conflicts resulting from previous attempts at denormalizing events from given sequences.
   * @return All unresolved conflicts for this model up-to the last denormalization for the associate store.
   */
  def conflicts: List[C]
}
