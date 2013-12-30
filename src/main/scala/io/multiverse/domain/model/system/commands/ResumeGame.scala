package io.multiverse.domain.model.system.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.system.{GameResumed, System, SystemEvent, SystemCommand, SystemId}

/**
 * Resumes the game.
 * @param systemId System being paused.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ResumeGame(systemId: SystemId, instanceId:InstanceId, timestamp:Long)
  extends SystemCommand with StaticTailCommand[System] {

  /**
   * The effect of this command.
   */
  val events: List[SystemEvent] =
    List(GameResumed(systemId, instanceId, timestamp))
}
