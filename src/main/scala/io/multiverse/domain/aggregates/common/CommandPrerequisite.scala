package io.multiverse.domain.aggregates.common

/**
 * Rule which prevents a specific type of command from being invoked under certain conditions.
 * @tparam A Aggregate type.
 * @tparam C Command type.
 */
trait CommandPrerequisite[-A, +C] {
  /**
   * Command whose validity is being tested.
   */
  val command: C

  /**
   * Tests the validity of a specific command rule given the context of the provided aggregate.
   * @param aggregate Aggregate the command is being tested against.
   * @return True if the rule passes, otherwise false.
   */
  def isValid(aggregate: A): Boolean
}
