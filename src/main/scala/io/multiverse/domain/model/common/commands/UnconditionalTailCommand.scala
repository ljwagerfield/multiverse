package io.multiverse.domain.model.common.commands

import io.multiverse.domain.model.common.AggregateMeta

/**
 * Command relating to an existing aggregate with an evaluation that is always valid.
 * @tparam A Aggregate meta type.
 */
trait UnconditionalTailCommand[A <: AggregateMeta] extends TailCommand[A] {

  /**
   * Unconditional commands have no prerequisites. Post-condition is strengthened so must not be overridden.
   */
  final val prerequisites: List[CommandPrerequisite[A#Root]] = Nil
}
