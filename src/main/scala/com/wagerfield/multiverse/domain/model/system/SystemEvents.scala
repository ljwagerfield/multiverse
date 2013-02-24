package com.wagerfield.multiverse.domain.model.system

import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Entire game paused.
 */
case class GamePaused(instanceId:InstanceId, systemId:SystemId, message:String) extends SystemEvent

/**
 * Entire game resumed.
 */
case class GameResumed(instanceId:InstanceId, systemId:SystemId) extends SystemEvent