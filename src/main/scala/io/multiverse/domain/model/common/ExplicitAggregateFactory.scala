package io.multiverse.domain.model.common

/**
 * Loads aggregates from given histories which start with an explicit creation event. The resulting object collections
 * are composed by a single root instance derived from type [[io.multiverse.domain.model.common.ChangeSet]]. Aggregates
 * without validation rules do not contain denormalized state, since state in this context is only used for validation.
 * Such aggregates can be instantiated without a history or snapshot; factories for these aggregates will therefore not
 * extend this trait.
 * @tparam A Aggregate meta type.
 */
trait ExplicitAggregateFactory[A <: AggregateMeta] extends AggregateFactory[A] {

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[A#Event, Aggregate[A]]

  /**
   * Loads an aggregate from a history represented by a sequence of events.
   * @param history Sequence of events representing a history to form the aggregate.
   * @return Aggregate denormalized from the sequence of events.
   */
  def from(history: Iterable[A#Event]): Aggregate[A] =
    (evaluate(history.head)/:history.tail)(_.evaluate(_)).committed
}
