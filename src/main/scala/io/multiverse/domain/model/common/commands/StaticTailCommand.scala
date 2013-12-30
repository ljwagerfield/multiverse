package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.AggregateMeta

/**
 * Unconditional command relating to an existing aggregate which evokes the same events on every evaluation.
 * @tparam A Aggregate meta type.
 */
trait StaticTailCommand[A <: AggregateMeta] extends UnconditionalTailCommand[A] {

  /**
   * The effect of this command.
   */
  val events: List[A#Event]

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  final protected def eventsFor(aggregateRoot: A): List[A#Event] = events
}
