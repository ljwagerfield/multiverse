package io.multiverse.domain.aggregates.common

/**
 * Command with an evaluation that is always valid.
 * @tparam E Event type.
 */
trait UnconditionalCommand[A <: AggregateRoot[A, E], E] {

  /**
   * Evaluates the effect of this command.
   * @param aggregate Lazily-evaluated aggregate. Only invoked by subtypes implementing [[io.multiverse.domain.aggregates.common.DynamicCommand]].
   * @return Evaluation of the events caused by invoking this command.
   */
  def evaluationFor(aggregate: () => A): List[E]
}
