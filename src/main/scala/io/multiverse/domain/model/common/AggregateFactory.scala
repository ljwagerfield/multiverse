package io.multiverse.domain.model.common

/**
 * Loads validated aggregates from given histories. The resulting object collections are composed by a
 * single root instance derived from type [[io.multiverse.domain.model.common.ChangeSet]]. Aggregates without
 * validation rules do not contain denormalized state, since state in this context is only used for validation. Such
 * aggregates can be instantiated without a history or snapshot; factories for these aggregates will therefore not extend
 * this trait.
 * @tparam A Aggregate meta type.
 */
trait AggregateFactory[A <: AggregateMeta] {

  /**
   * Loads an aggregate from a history represented by a sequence of events.
   * @param history Sequence of events representing a history to form the aggregate.
   * @return Aggregate denormalized from the sequence of events.
   */
  def from(history: Iterable[A#Event]): Aggregate[A]
}
