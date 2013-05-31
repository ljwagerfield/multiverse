package io.multiverse.domain.aggregates.common

/**
 * Root object marshaling mutations to the coherent collection of all module objects (i.e. the aggregate).
 * @tparam T The derived class type.
 * @tparam E The event type.
 */
trait AggregateRoot[T <: AggregateRoot[T, E], E] extends EventSourced[T, E] {
  /**
   * Events pending commitment.
   * @return Sequence of uncommitted events.
   */
  def changes: List[E]

  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: T
}
