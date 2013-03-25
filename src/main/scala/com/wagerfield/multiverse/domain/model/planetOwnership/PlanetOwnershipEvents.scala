package com.wagerfield.multiverse.domain.model.planetOwnership

import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId
import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Planet colonized.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param planetId Planet the ship is bound for.
 * @param colonizationOrder Order which invoked the inbound ship.
 */
case class PlanetColonized(instanceId:InstanceId, timeStamp:Long, planetId:PlanetId, colonizationOrder:ShipEvent) extends PlanetOwnershipEvent

/**
 * Planet abandoned.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param planetId The planet being abandoned by its last occupants.
 */
case class PlanetAbandoned(instanceId:InstanceId, timeStamp:Long, planetId:PlanetId) extends PlanetOwnershipEvent
