package baseSpecifications

import io.multiverse.domain.model.common.{Aggregate, AggregateMeta}
import org.specs2.mutable.Specification
import io.multiverse.domain.model.common.commands.CommandCombinators.CommandChain
import io.multiverse.domain.model.common.commands.{StaticTailCommand, CommandPrerequisite, TailCommand, HeadCommand}
import scala.language.implicitConversions

object CommandCombinators {

  /**
   * Converts the given command into a test chain segment.
   * @param headCommand Command to convert into a segment.
   * @tparam A Aggregate meta type.
   * @return Command chain segment.
   */
  implicit def headCommandToTestChain[A <: AggregateMeta](headCommand: HeadCommand[A]): TestCommandChain[A] =
    new TestCommandChain[A](headCommand.evaluation)

  /**
   * Converts the given command into a test chain segment.
   * @param tailCommand Command to convert into a segment.
   * @tparam A Aggregate meta type.
   * @return Command chain segment.
   */
  implicit def staticCommandToTestChain[A <: AggregateMeta](tailCommand: StaticTailCommand[A]): TestEvaluatedCommandChain[A] =
    new TestEvaluatedCommandChain[A](tailCommand.events)

  /**
   * Converts the given aggregate into a test chain segment.
   * @param aggregate Aggregate to convert into a segment.
   * @tparam A Aggregate meta type.
   * @return Test chain segment.
   */
  implicit def aggregateToTestChain[A <: AggregateMeta](aggregate: Aggregate[A]): TestCommandChain[A] =
    new TestCommandChain[A](aggregate)

  /**
   * Converts the chain segment into a test chain segment.
   * @param segment Chain segment to convert.
   * @tparam A Aggregate meta type.
   * @return Test chain segment.
   */
  implicit def chainToTestChain[A <: AggregateMeta](segment: CommandChain[A]): TestCommandChain[A] =
    new TestCommandChain[A](segment.aggregate)

  /**
   * Command chain segment representing a tail command.
   * @tparam A Aggregate meta type.
   */
  class TestEvaluatedCommandChain[A <: AggregateMeta](val events: List[A#Event]) extends Specification {

    /**
     * Asserts the aggregate yields the given event sequence.
     * @param events Events the aggregate must yield.
     */
    def yields(events: List[A#Event]): Unit =
      events must be equalTo events

    /**
     * Asserts the aggregate yields the given event.
     * @param event Event the aggregate must yield.
     */
    def yields(event: A#Event): Unit = yields(List(event))
  }

  /**
   * Command chain segment representing a tail command.
   * @tparam A Aggregate meta type.
   */
  class TestCommandChain[A <: AggregateMeta](val aggregate: Aggregate[A]) extends Specification {

    /**
     * Asserts the aggregate yields the given event sequence.
     * @param events Events the aggregate must yield.
     */
    def yields(events: List[A#Event]): Unit =
      aggregate.changes must be equalTo events

    /**
     * Asserts the aggregate yields the given event.
     * @param event Event the aggregate must yield.
     */
    def yields(event: A#Event): Unit = yields(List(event))

    /**
     * Creates a new failed command chain.
     * @param command Command which cannot be evaluated.
     * @return Tail command chain segment.
     */
    def cannot[C <: TailCommand[A]](command: C): FailedCommandChain[A, C] =
      new FailedCommandChain[A, C](aggregate, command)
  }

  /**
   * Chain segment representing a command that cannot be evaluated.
   * @param aggregate Aggregate incompatible with the provided command.
   * @param command Command which cannot be evaluated.
   * @tparam A Aggregate meta type.
   * @tparam C Command type.
   */
  class FailedCommandChain[A <: AggregateMeta, C <: TailCommand[A]](val aggregate: Aggregate[A], val command: C) extends Specification {

    /**
     * Asserts a command prerequisite failure.
     * @param expectedFailure Predicate expected to fail;
     */
    def because(expectedFailure: C => CommandPrerequisite[A#Root]): Unit = {
      command failedPrerequisitesFor aggregate.root must be equalTo List(expectedFailure(command))
      command evaluationFor aggregate must throwA[Exception]
    }
  }
}
