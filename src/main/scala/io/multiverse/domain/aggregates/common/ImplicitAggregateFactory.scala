package io.multiverse.domain.aggregates.common

/**
 * Loads aggregates from given histories. The resulting object collections are composed by a single root instance derived
 * from type [[io.multiverse.domain.aggregates.common.AggregateRoot]]. Aggregates without validation rules do not contain denormalized
 * state, since state in this context is only used for validation. Such aggregates can be instantiated without a history
 * or snapshot; factories for these aggregates will therefore not extend this trait.
 * @tparam A Aggregate root type.
 * @tparam E Event type.
 */
trait ImplicitAggregateFactory[A <: AggregateRoot[A, E], E] extends AggregateFactory[A, E] {
  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent:E): A

  /**
   * Loads an aggregate from a history represented by a sequence of events.
   * @param history Sequence of events representing a history to form the aggregate.
   * @return Aggregate denormalized from the sequence of events.
   */
  def from(history: Iterable[E]): A = {
    (getPrototype(history.head)/:history)(_.applyEvent(_)).markCommitted
  }
}
