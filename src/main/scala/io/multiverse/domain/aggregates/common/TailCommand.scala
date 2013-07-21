package io.multiverse.domain.aggregates.common

/**
 * Command relating to an existing aggregate.
 * @tparam A Aggregate type,
 * @tparam E Event type.
 */
trait TailCommand[A <: AggregateRoot[A, E], E] {

  /**
   * Applies this command to the given aggregate.
   * @param aggregate Aggregate to apply the command to.
   * @return Aggregate with command applied.
   */
  def realizationFor(aggregate: A): A
}
