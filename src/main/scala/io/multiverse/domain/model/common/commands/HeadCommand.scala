package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.AggregateRoot

/**
 * Command with an evaluation that describes the creation of a new aggregate. These commands are constant and
 * unconditional by nature.
 * @tparam A Aggregate type.
 * @tparam E Event type.
 */
trait HeadCommand[A <: AggregateRoot[A, E], E] extends ConstantCommand[A, E] {

  /**
   * Applies this command to a new aggregate.
   * @return New aggregate with command applied.
   */
  def realization: A
}
