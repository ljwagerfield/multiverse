package baseSpecifications

import io.multiverse.domain.model.common.AggregateRoot
import org.specs2.mutable.Specification
import io.multiverse.domain.model.common.commands.{CommandPrerequisite, ConditionalTailCommand, DynamicCommand, HeadCommand, TailCommand, UnconditionalTailCommand}

/**
 * Specification asserting the validity of command behaviour.
 */
trait CommandSpecification extends Specification {
  /**
   * Asserts the successful evaluation and realization of head commands.
   * @param command Head command.
   * @param events Expected evaluation.
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @tparam C Head command type.
   * @return Match result.
   */
  protected def assertSuccess[A <: AggregateRoot[A, E], E, C <: HeadCommand[A, E]](command: C, events: List[E]) = {
    command.evaluation must be equalTo events
    command.realization.changes must be equalTo events
  }

  /**
   * Asserts the successful evaluation and realization of conditional tail commands.
   * @param aggregate Aggregate to evaluate the command against.
   * @param command Head command.
   * @param events Expected evaluation.
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @tparam C Tail command type.
   * @return Match result.
   */
  protected def assertSuccess[A <: AggregateRoot[A, E], E, C <: ConditionalTailCommand[A, E, C]](aggregate: A, command: C, events: List[E]) = {
    command failedPrerequisitesFor aggregate must beEmpty
    command evaluationFor aggregate must be equalTo events
    (command realizationFor aggregate).changes must be equalTo events
  }

  /**
   * Asserts the successful evaluation and realization of unconditional tail commands.
   * @param aggregate Aggregate to evaluate the command against.
   * @param command Head command.
   * @param events Expected evaluation.
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @tparam C Tail command type.
   * @return Match result.
   */
  protected def assertUnconditional[A <: AggregateRoot[A, E], E, C <: UnconditionalTailCommand[A, E, C]](aggregate: A, command: C, events: List[E]) = {
    command evaluationFor aggregate must be equalTo events
    (command realizationFor aggregate).changes must be equalTo events
  }

  /**
   * Asserts the successful evaluation and realization of idempotent tail commands.
   * @param aggregate Aggregate to evaluate the command against.
   * @param command Head command.
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @tparam C Tail command type.
   * @return Match result.
   */
  protected def assertIdempotent[A <: AggregateRoot[A, E], E, C <: DynamicCommand[A, E, C] with TailCommand[A]](aggregate: A, command: C) = {
    aggregate.changes must beEmpty
    command evaluationFor aggregate must beEmpty
    (command realizationFor aggregate).changes must beEmpty
  }

  /**
   * Asserts the failed prerequisites of tail commands.
   * @param aggregate Aggregate to evaluate the command against.
   * @param command Head command.
   * @param expectedFailuresFor Function returning the expected failed prerequisites for this command-aggregate pair.
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @tparam C Head command type.
   * @return Match result.
   */
  protected def assertFailure[A <: AggregateRoot[A, E], E, C <: DynamicCommand[A, E, C]](aggregate: A, command: C, expectedFailuresFor: C => List[CommandPrerequisite[A, C]]) = {
    command failedPrerequisitesFor aggregate must be equalTo expectedFailuresFor(command)
    command evaluationFor aggregate must throwA[Exception]
  }
}
