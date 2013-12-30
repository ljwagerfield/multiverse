package io.multiverse.domain.model.common

/**
 * Object with event sourced state.
 * @tparam A Aggregate meta type.
 */
trait Aggregate[A <: AggregateMeta] extends ChangeSet[A#Event, Aggregate[A]]  {

  /**
   * Root object within the aggregate graph.
   */
  val root: A#Root

  /**
   * Evaluates the event's mutative effects by applying any changes to the new resulting instance.
   */
  val evaluate: PartialFunction[A#Event, Aggregate[A]] = PartialFunction.empty
}
