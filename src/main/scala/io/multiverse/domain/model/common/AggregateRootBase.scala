package io.multiverse.domain.model.common

/**
 * Convenience class for aggregate meta implementations seeking a more terse declaration.
 * @tparam A Combined aggregate root and meta type.
 * @tparam E Event type.
 */
abstract class AggregateRootBase[A, E] extends AggregateMeta {
  self: A =>

  /**
   * Event type used to build aggregate state.
   */
  type Event = E

  /**
   * Root object in aggregate graph.
   */
  type Root = A

  /**
   * Root object within the aggregate graph.
   */
  val root: Root = this
}
