package io.multiverse.domain.model.common.commands

import scala.language.implicitConversions
import io.multiverse.domain.model.common.AggregateRoot

/**
 * Fluent interface for evaluating multiple commands.
 */
object CommandConversions {

  /**
   * Converts the given command into a chain segment.
   * @param headCommand Command to convert into a segment.
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @return Head command chain segment.
   */
  implicit def headCommandToChain[A <: AggregateRoot[A, E], E](headCommand: HeadCommand[A, E]): CommandChain[A] =
    new CommandChain[A](headCommand.realization)

  /**
   * Converts the given aggregate root into a chain segment.
   * @param aggregateRoot Aggregate root to convert into a segment.
   * @tparam A Aggregate type.
   * @return Aggregate root chain segment.
   */
  implicit def aggregateRootToChain[A <: AggregateRoot[A, _]](aggregateRoot: A): AggregateRootCommandChain[A] =
    new AggregateRootCommandChain[A](aggregateRoot)

  /**
   * Converts the chain segment into an aggregate root.
   * @param segment Tail command chain segment to convert.
   * @tparam A Aggregate type.
   * @return Aggregate root chain segment.
   */
  implicit def chainToAggregateRoot[A <: AggregateRoot[A, _]](segment: CommandChain[A]): A =
    segment.realization

  /**
   * Converts the given head command into an aggregate root.
   * @param headCommand Head command to convert.
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @return Aggregate root chain segment.
   */
  implicit def headCommandToAggregateRoot[A <: AggregateRoot[A, E], E](headCommand: HeadCommand[A, E]): A =
    headCommand.realization

  /**
   * Command chain segment representing a tail command.
   * @tparam A Aggregate type.
   */
  class CommandChain[A <: AggregateRoot[A, _]](aggregateRoot: A) {
    /**
     * Ends the command chain by returning the realized aggregate root.
     * @return Aggregate root.
     */
    def realization: A = aggregateRoot

    /**
     * Realizes the given command using the chained aggregate root.
     * @param tailCommand Command to realize.
     * @return Tail command chain segment.
     */
    def />(tailCommand: TailCommand[A]): CommandChain[A] =
      new CommandChain[A](tailCommand realizationFor aggregateRoot)
  }

  /**
   * Command chain segment representing an aggregate root.
   * @param aggregateRoot Aggregate root to convert into a segment.
   * @tparam A Aggregate type.
   */
  class AggregateRootCommandChain[A <: AggregateRoot[A, _]](aggregateRoot: A) {

    /**
     * Realizes the given command using the chained aggregate root.
     * @param tailCommand Command to realize.
     * @return Tail command chain segment.
     */
    def />(tailCommand: TailCommand[A]): CommandChain[A] =
      new CommandChain[A](tailCommand realizationFor aggregateRoot)
  }
}
