package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.{AggregateMeta, Aggregate}

/**
 * Command relating to an existing aggregate.
 * @tparam A Aggregate meta type.
 */
trait TailCommand[A <: AggregateMeta] {

  /**
   * Predicates that must pass for the command to be successfully evaluated.
   */
  val prerequisites: List[CommandPrerequisite[A#Root]]

  /**
   * Command prerequisites which fail for the given aggregate.
   * @param aggregateRoot Aggregate containing state to validate prerequisites against.
   * @return Failed prerequisites.
   */
  def failedPrerequisitesFor(aggregateRoot: A#Root): List[CommandPrerequisite[A#Root]] =
    prerequisites.filter(!_.isValid(aggregateRoot))

  /**
   * Applies this command to the given aggregate.
   * @param aggregate Aggregate to apply the command to.
   * @return Aggregate with command applied.
   */
  def evaluationFor(aggregate: Aggregate[A]): Aggregate[A] = {
    require(failedPrerequisitesFor(aggregate.root).isEmpty, "Command must be valid for this aggregate.")
    (aggregate /: eventsFor(aggregate.root))(_ evaluate _)
  }

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: A#Root): List[A#Event]
}
