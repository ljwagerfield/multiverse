package com.wagerfield.multiverse.domain.model.system

import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Entire game paused.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param systemId System being paused.
 * @param message Message for users explaining why the game has been paused.
 */
case class GamePaused(instanceId:InstanceId, timeStamp:Long, systemId:SystemId, message:String) extends SystemEvent

/**
 * Entire game resumed.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param systemId System being resumed.
 */
case class GameResumed(instanceId:InstanceId, timeStamp:Long, systemId:SystemId) extends SystemEvent
