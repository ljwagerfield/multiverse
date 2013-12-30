package io.multiverse.domain.model.system.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.system.{System, SystemEvent, GamePaused, SystemCommand, SystemId}

/**
 * Pauses the game.
 * @param systemId System being paused.
 * @param message Message for users explaining why the game has been paused.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class PauseGame(systemId: SystemId, message:String, instanceId:InstanceId, timestamp:Long)
  extends SystemCommand with StaticTailCommand[System] {

  /**
   * The effect of this command.
   */
  val events: List[SystemEvent] =
    List(GamePaused(systemId, message, instanceId, timestamp))
}
