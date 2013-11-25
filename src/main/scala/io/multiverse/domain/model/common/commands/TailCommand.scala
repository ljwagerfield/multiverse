package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.AggregateRoot

/**
 * Command relating to an existing aggregate.
 * @tparam A Aggregate type,
 */
trait TailCommand[A <: AggregateRoot[A, _]] {

  /**
   * Applies this command to the given aggregate.
   * @param aggregate Aggregate to apply the command to.
   * @return Aggregate with command applied.
   */
  def realizationFor(aggregate: A): A
}
