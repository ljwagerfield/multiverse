package io.multiverse.domain.model.planetOwnership

import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.ship.{ShipEvent, ShipId}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.PlanetColonizationOrdered

/**
 * Planet colonized.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param planetId Planet the ship is bound for.
 * @param colonizationOrder Order which invoked the inbound ship.
 */
case class PlanetColonized(instanceId:InstanceId, timeStamp:Long, planetId:PlanetId, colonizationOrder:PlanetColonizationOrdered) extends PlanetOwnershipEvent

/**
 * Planet abandoned.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param planetId The planet being abandoned by its last occupants.
 */
case class PlanetAbandoned(instanceId:InstanceId, timeStamp:Long, planetId:PlanetId) extends PlanetOwnershipEvent
