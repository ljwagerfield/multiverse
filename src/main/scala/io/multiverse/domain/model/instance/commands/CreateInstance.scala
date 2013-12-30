package io.multiverse.domain.model.instance.commands

import io.multiverse.domain.model.common.Aggregate
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.instance.{Version, InstanceCreated, Instance, InstanceCommand, InstanceId}

/**
 * Creates a new instance.
 * @param version Version of the codebase this instance is running on.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class CreateInstance(version: Version, instanceId: InstanceId, timestamp: Long)
  extends InstanceCommand with HeadCommand[Instance] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  lazy val evaluation: Aggregate[Instance] = Instance(InstanceCreated(instanceId, version, timestamp))
}
