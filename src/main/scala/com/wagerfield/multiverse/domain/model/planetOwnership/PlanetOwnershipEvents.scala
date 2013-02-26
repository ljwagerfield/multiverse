package com.wagerfield.multiverse.domain.model.planetOwnership

import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId
import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Ship bound for planet: ping event type; ignore if last order for ship does not match one provided.
 * @param instanceId Instance the event occurred in.
 * @param planetId Planet the ship is bound for.
 * @param shipId The inbound ship.
 * @param shipOrder The order which set the ship inbound for this planet.
 */
case class ShipBoundForPlanet(instanceId:InstanceId, planetId:PlanetId, shipId:ShipId, shipOrder:ShipEvent) extends PlanetOwnershipEvent

/**
 * Planet abandoned.
 * @param instanceId Instance the event occurred in.
 * @param planetId The planet being abandoned by its last occupants.
 */
case class PlanetAbandoned(instanceId:InstanceId, planetId:PlanetId) extends PlanetOwnershipEvent