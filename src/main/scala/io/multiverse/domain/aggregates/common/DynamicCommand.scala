package io.multiverse.domain.aggregates.common

/**
 * Command with an evaluation that is conditionally valid for its associated aggregate.
 * @tparam A Aggregate type.
 * @tparam E Event type.
 * @tparam C Command subtype.
 */
trait DynamicCommand[A <: AggregateRoot[A, E], E, C <: DynamicCommand[A, E, C]] {
  self: C =>

  /**
   * Prerequisites suggest all the potential ways in which parameters may be invalidated and consequently handled by the
   * caller. Commands with no prerequisites are always valid and therefore require no additional management once evoked.
   * Such commands are suitable for automated invocations, such as those evoked by compensation strategies.
   */
  val prerequisites: List[CommandPrerequisite[A, C]]

  /**
   * Command prerequisites which fail for the given aggregate.
   * @param aggregate Aggregate containing state to validate prerequisites against.
   * @return Failed prerequisites.
   */
  def failedPrerequisitesFor(aggregate: A): List[CommandPrerequisite[A, C]] =
    prerequisites.filter(!_.isValid(aggregate))

  /**
   * Evaluates the effect of this command against the given aggregate.
   * @param aggregate Aggregate to evaluate command against.
   * @return Evaluation of the events caused by invoking this command against the given aggregate.
   */
  def evaluationFor(aggregate: A): List[E] = {
    require(failedPrerequisitesFor(aggregate).isEmpty, "Command must be valid for this aggregate.")
    evaluationForValidInput(aggregate)
  }

  /**
   * Evaluates the effect of this command against the given aggregate, assuming no failing prerequisites.
   * @param aggregate Aggregate to evaluate command against.
   * @return Evaluation of the events caused by invoking this command against the given aggregate.
   */
  protected def evaluationForValidInput(aggregate: A): List[E]
}
