package com.wagerfield.multiverse.domain.model.planetOwnership

import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId
import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Ship bound for planet: ping event type; ignore if last journey for ship does not match one provided.
 */
case class ShipBoundForPlanet(instanceId:InstanceId, planetId:PlanetId, shipId:ShipId, shipOrder:ShipEvent) extends PlanetOwnershipEvent

/**
 * Planet abandoned.
 */
case class PlanetAbandoned(instanceId:InstanceId, planetId:PlanetId) extends PlanetOwnershipEvent