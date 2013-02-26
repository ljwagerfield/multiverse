package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Solar system created.
 * @param instanceId Instance the event occurred in.
 * @param starId Unique ID for the new star.
 * @param nearStarIds Other stars linked to this solar system by wormholes.
 *                    No near stars indicates centre of new galaxy.
 *                    There is no limit to number of stars per galaxy.
 *                    Stars cannot be near themselves
 *                    Galaxy must form planar graph with max degrees 4.
 * @param planetIds Planets in order from distance from star - attributes are randomly assigned.
 */
case class SolarSystemCreated(instanceId:InstanceId, starId: StarId, nearStarIds: List[StarId], planetIds: List[PlanetId]) extends SolarSystemEvent

/**
 * Planet named.
 * @param instanceId Instance the event occurred in.
 * @param starId The planet's solar system.
 * @param planetId The planet being named.
 * @param name New name for the planet, unique within this solar system.
 */
case class PlanetNamed(instanceId:InstanceId, starId: StarId, planetId: PlanetId, name: String) extends SolarSystemEvent

/**
 * Star named.
 * @param instanceId Instance the event occurred in.
 * @param starId The star being named.
 * @param name New name for the star. Eventually consistent across solar systems.
 */
case class StarNamed(instanceId:InstanceId, starId: StarId, name: String) extends SolarSystemEvent

/**
 * Star name duplicate renamed.
 * @param instanceId Instance the event occurred in.
 * @param starId The star which is having its name changed to resolve the duplicate name inconsistency.
 * @param conflictingStarId The conflicting star which is keeping its name.
 * @param name New name for the star.
 */
case class StarNameDuplicateRenamed(instanceId:InstanceId, starId: StarId, conflictingStarId: StarId, name: String) extends SolarSystemEvent

/**
 * Ship bound for solar system: ping event type; ignore if last order for ship does not match one provided.
 * @param instanceId Instance the event occurred in.
 * @param starId The star the ship is bound for.
 * @param shipId The inbound ship.
 * @param shipOrder The order which commanded the ship for this star.
 */
case class ShipBoundForSolarSystem(instanceId:InstanceId, starId: StarId, shipId: ShipId, shipOrder: ShipEvent) extends SolarSystemEvent