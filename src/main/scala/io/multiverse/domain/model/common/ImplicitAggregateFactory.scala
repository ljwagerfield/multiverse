package io.multiverse.domain.model.common

/**
 * Loads aggregates from given histories. The resulting object collections are composed by a single root instance derived
 * from type [[io.multiverse.domain.model.common.ChangeSet]]. Aggregates without validation rules do not contain denormalized
 * state, since state in this context is only used for validation. Such aggregates can be instantiated without a history
 * or snapshot; factories for these aggregates will therefore not extend this trait.
 * @tparam A Aggregate meta type.
 */
trait ImplicitAggregateFactory[A <: AggregateMeta] extends AggregateFactory[A] {

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent:A#Event): Aggregate[A]

  /**
   * Loads an aggregate from a history represented by a sequence of events.
   * @param history Sequence of events representing a history to form the aggregate.
   * @return Aggregate denormalized from the sequence of events.
   */
  def from(history: Iterable[A#Event]): Aggregate[A] =
    (getPrototype(history.head)/:history)(_.evaluate(_)).committed
}
