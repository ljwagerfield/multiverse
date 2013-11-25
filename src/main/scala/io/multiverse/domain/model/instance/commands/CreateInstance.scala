package io.multiverse.domain.model.instance.commands

import io.multiverse.domain.model.common.commands.{HeadCommand, CommandPrerequisite, ConditionalTailCommand}
import io.multiverse.domain.model.instance.{Version, InstanceCreated, InstanceEvent, Instance, InstanceCommand, InstanceId}

/**
 * Creates a new instance.
 * @param version Version of the codebase this instance is running on.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class CreateInstance(version: Version, instanceId: InstanceId, timestamp: Long)
  extends InstanceCommand with HeadCommand[Instance, InstanceEvent] {

  /**
   * Constant result.
   */
  private val event = InstanceCreated(instanceId, version, timestamp)

  /**
   * Evaluates the effect of this command.
   * @return Evaluation of the events caused by invoking this command.
   */
  def evaluation: List[InstanceEvent] = List(event)

  /**
   * Applies this command to a new aggregate.
   * @return New aggregate with command applied.
   */
  def realization: Instance = Instance.apply(event)
}
