package com.wagerfield.multiverse.domain.model.system

import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Entire game paused.
 * @param instanceId Instance the event occurred in.
 * @param systemId The system being paused.
 * @param message The message for users explaining why the game has been paused.
 */
case class GamePaused(instanceId:InstanceId, systemId:SystemId, message:String) extends SystemEvent

/**
 * Entire game resumed.
 * @param instanceId Instance the event occurred in.
 * @param systemId The system being resumed.
 */
case class GameResumed(instanceId:InstanceId, systemId:SystemId) extends SystemEvent
