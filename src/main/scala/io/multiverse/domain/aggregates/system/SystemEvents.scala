package io.multiverse.domain.aggregates.system

import io.multiverse.domain.aggregates.instance.InstanceId

/**
 * Entire game paused.
 * @param systemId System being paused.
 * @param message Message for users explaining why the game has been paused.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class GamePaused(systemId: SystemId, message: String, instanceId: InstanceId, timestamp: Long) extends SystemEvent

/**
 * Entire game resumed.
 * @param systemId System being resumed.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class GameResumed(systemId: SystemId, instanceId: InstanceId, timestamp: Long) extends SystemEvent
