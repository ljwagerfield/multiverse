package io.multiverse.domain.aggregates.common

/**
 * Command relating to an existing aggregate with an evaluation that is conditionally valid.
 * @tparam A Aggregate type,
 * @tparam E Event type.
 * @tparam C Command type.
 */
trait ConditionalTailCommand[A <: AggregateRoot[A, E], E, C <: ConditionalTailCommand[A, E, C]]
  extends DynamicCommand[A, E, C] with TailCommand[A, E] {
  self: C =>

  /**
   * Applies this command to the given aggregate.
   * @param aggregate Aggregate to apply the command to.
   * @return Aggregate with command applied.
   */
  def realizationFor(aggregate: A): A =
    (aggregate/:evaluationFor(aggregate))(_ apply _)
}
