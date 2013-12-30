package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.{AggregateMeta, Aggregate}
import scala.language.implicitConversions

/**
 * Fluent interface for evaluating multiple commands.
 */
object CommandCombinators {

  /**
   * Converts the given command into a chain segment.
   * @param headCommand Command to convert into a segment.
   * @tparam A Aggregate meta type.
   * @return Command chain segment.
   */
  implicit def headCommandToChain[A <: AggregateMeta](headCommand: HeadCommand[A]): CommandChain[A] =
    new CommandChain[A](headCommand.evaluation)

  /**
   * Converts the given aggregate into a chain segment.
   * @param aggregate Aggregate to convert into a segment.
   * @tparam A Aggregate meta type.
   * @return Command chain segment.
   */
  implicit def aggregateToChain[A <: AggregateMeta](aggregate: Aggregate[A]): CommandChain[A] =
    new CommandChain[A](aggregate)

  /**
   * Converts the chain segment into an aggregate root.
   * @param segment Chain segment to convert.
   * @tparam A Aggregate meta type.
   * @return Aggregate.
   */
  implicit def chainToAggregate[A <: AggregateMeta](segment: CommandChain[A]): Aggregate[A] =
    segment.aggregate

  /**
   * Converts the given head command into an aggregate root.
   * @param headCommand Head command to convert.
   * @tparam A Aggregate meta type.
   * @return Aggregate.
   */
  implicit def headCommandToAggregate[A <: AggregateMeta](headCommand: HeadCommand[A]): Aggregate[A] =
    headCommand.evaluation

  /**
   * Command chain segment representing a tail command.
   * @tparam A Aggregate meta type.
   */
  class CommandChain[A <: AggregateMeta](val aggregate: Aggregate[A]) {

    /**
     * Realizes the given command using the chained aggregate root.
     * @param tailCommand Command to realize.
     * @return Tail command chain segment.
     */
    def after(tailCommand: TailCommand[A]): CommandChain[A] =
      new CommandChain[A](tailCommand evaluationFor aggregate)

    /**
     * Realizes the given command using the chained aggregate root.
     * @param tailCommand Command to realize.
     * @return Tail command chain segment.
     */
    def after(tailCommand: Commit): CommandChain[A] =
      new CommandChain[A](aggregate.committed)
  }
}
