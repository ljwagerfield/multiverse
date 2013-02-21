package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Solar system created: no near stars indicates centre of new galaxy; no limit to number of stars per galaxy; stars cannot be near themselves; galaxy must form planar graph with max degrees 4; planets in order from distance from star; planet attributes dynamically generated. 
 */
case class SolarSystemCreated(instanceId:InstanceId, starId: StarId, nearStarIds: List[StarId], planetIds: List[PlanetId]) extends SolarSystemEvent

/**
 * Planet named: name is unique within aggregate.
 */
case class PlanetNamed(instanceId:InstanceId, starId: StarId, planetId: PlanetId, name: String) extends SolarSystemEvent

/**
 * Star named: name eventually unique across aggregates.
 */
case class StarNamed(instanceId:InstanceId, starId: StarId, name: String) extends SolarSystemEvent

/**
 * Star name duplicate renamed.
 */
case class StarNameDuplicateRenamed(instanceId:InstanceId, starId: StarId, conflictingStarId: StarId, name: String) extends SolarSystemEvent

/**
 * Ship bound for solar system: ping event type; ignore if last journey for ship does not match one provided.
 */
case class ShipBoundForSolarSystem(instanceId:InstanceId, starId: StarId, shipId: ShipId, shipOrder: ShipEvent) extends SolarSystemEvent