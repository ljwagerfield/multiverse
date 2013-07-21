package io.multiverse.domain.aggregates.common

/**
 * Unconditional command relating to an existing aggregate which evokes the same events on every evaluation.
 * @tparam A Aggregate type.
 * @tparam E Event type.
 */
trait ConstantTailCommand[A <: AggregateRoot[A, E], E] extends ConstantCommand[A, E] with TailCommand[A, E] {

  /**
   * Applies this command to the given aggregate.
   * @param aggregate Aggregate to apply the command to.
   * @return Aggregate with command applied.
   */
  def realizationFor(aggregate: A): A =
    (aggregate/:evaluation)(_ apply _)
}
