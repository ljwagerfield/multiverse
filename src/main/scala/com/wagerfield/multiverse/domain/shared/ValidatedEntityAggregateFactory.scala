package com.wagerfield.multiverse.domain.shared

/**
 * Loads validated entity aggregates from given histories. The resulting object collections are composed by a
 * single root instance derived from type [[com.wagerfield.multiverse.domain.shared.AggregateRoot]]. Aggregates without
 * validation rules do not contain denormalized state, since state in this context is only used for validation. Such
 * aggregates can be instantiated without a history or snapshot; factories for these aggregates will therefore not extend
 * this trait.
 * @tparam A The aggregate root type.
 * @tparam E The event type.
 */
trait ValidatedEntityAggregateFactory[A <: AggregateRoot[A, E], E] extends ValidatedAggregateFactory[A, E] with EventSourced[A, E] {
  /**
   * Loads an aggregate from a history represented by a sequence of events.
   * @param history Sequence of events representing a history to form the aggregate.
   * @return Aggregate denormalized from the sequence of events.
   */
  def from(history: Iterable[E]): A = {
    (applyEvent(history.head)/:history)(_.applyEvent(_)).markCommitted // May need to skip head in folded history.
  }
}