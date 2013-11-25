package io.multiverse.domain.model.planetOwnership

import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.ship.{ShipEvent, ShipId}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.PlanetColonizationOrdered

/**
 * Planet colonized.
 * @param planetId Planet the ship is bound for.
 * @param colonizationOrder Order which invoked the inbound ship.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class PlanetColonized(planetId:PlanetId, colonizationOrder:PlanetColonizationOrdered, instanceId:InstanceId, timestamp:Long) extends PlanetOwnershipEvent

/**
 * Planet abandoned.
 * @param planetId The planet being abandoned by its last occupants.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class PlanetAbandoned(planetId:PlanetId, instanceId:InstanceId, timestamp:Long) extends PlanetOwnershipEvent
