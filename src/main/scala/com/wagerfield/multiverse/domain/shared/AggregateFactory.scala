package com.wagerfield.multiverse.domain.shared

/**
 * Loads aggregates from given histories. The resulting object collections are composed by a single root instance
 * derived from type [[com.wagerfield.multiverse.domain.shared.AggregateRoot]].
 * @tparam A The aggregate root type.
 * @tparam E The event type.
 */
trait AggregateFactory[A <: AggregateRoot[A, E], E] extends EventSourced[A, E] {
  /**
   * Loads an aggregate from a history represented by a sequence of events.
   * @param history Sequence of events representing a history to form the aggregate.
   * @return Aggregate denormalized from the sequence of events.
   */
  def loadFromHistory(history: Iterable[E]): A = {
    (applyEvent(history.head)/:history)(_.applyEvent(_)).markCommitted // May need to skip head in folded history.
  }
}
