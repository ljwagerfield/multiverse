package io.multiverse.domain.model.common.commands

/**
 * Rule which prevents a specific type of command from being invoked under certain conditions.
 * @tparam A Aggregate root type.
 */
trait CommandPrerequisite[-A] {

  /**
   * Tests the validity of a specific command rule given the context of the provided aggregate.
   * @param aggregateRoot Aggregate the command is being tested against.
   * @return True if the rule passes, otherwise false.
   */
  def isValid(aggregateRoot: A): Boolean
}
