package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.AggregateRoot

/**
 * Command relating to an existing aggregate with an evaluation that is always valid.
 * @tparam A Aggregate type.
 * @tparam E Event type.
 * @tparam C Command type.
 */
trait UnconditionalTailCommand[A <: AggregateRoot[A, E], E, C <: UnconditionalTailCommand[A, E, C]]
  extends DynamicCommand[A, E, C] with UnconditionalCommand[A, E] with TailCommand[A] {
  self: C =>

  /**
   * Unconditional commands have no prerequisites. Post-condition is strengthened so must not be overridden; such
   * implementations would be in violation of LSP.
   */
  val prerequisites: List[CommandPrerequisite[A, C]] = Nil

  /**
   * Evaluates the effect of this command.
   * @param aggregate Lazily-evaluated aggregate. Only invoked by subtypes implementing [[io.multiverse.domain.model.common.commands.DynamicCommand]].
   * @return Evaluation of the events caused by invoking this command.
   */
  override def evaluationFor(aggregate: () => A): List[E] =
    evaluationFor(aggregate())

  /**
   * Applies this command to the given aggregate.
   * @param aggregate Aggregate to apply the command to.
   * @return Aggregate with command applied.
   */
  def realizationFor(aggregate: A): A =
    (aggregate/:evaluationFor(aggregate))(_ apply _)
}
