package io.multiverse.domain.aggregates.common

/**
 * Root object marshaling mutations to the coherent collection of all module objects (i.e. the aggregate).
 * @tparam A Aggregate subtype.
 * @tparam E Event type.
 */
trait AggregateRoot[A <: AggregateRoot[A, E], E] extends EventSourced[A, E] {

  /**
   * Events pending commitment.
   * @return Sequence of uncommitted events.
   */
  def changes: List[E]

  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: A
}
