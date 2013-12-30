package io.multiverse.domain.model.common

/**
 * Type information for an aggregate.
 */
trait AggregateMeta {

  /**
   * Event type used to build aggregate state.
   */
  type Event

  /**
   * Root object in aggregate graph.
   */
  type Root
}
