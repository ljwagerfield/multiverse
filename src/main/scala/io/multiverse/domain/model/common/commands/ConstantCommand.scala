package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.AggregateRoot

/**
 * Unconditional command which evokes the same events on every evaluation.
 * @tparam A Aggregate type.
 * @tparam E Event type.
 */
trait ConstantCommand[A <: AggregateRoot[A, E], E] extends UnconditionalCommand[A, E] {

  /**
   * Evaluates the effect of this command.
   * @return Evaluation of the events caused by invoking this command.
   */
  def evaluation: List[E]

  /**
   * Evaluates the effect of this command.
   * @param aggregate Lazily-evaluated aggregate.
   * @return Evaluation of the events caused by invoking this command.
   */
  override def evaluationFor(aggregate: () => A): List[E] = evaluation
}
