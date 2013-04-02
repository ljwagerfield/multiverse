package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.{ShortAlphanumericName, ShortAlphabeticName}

/**
 * Solar system created.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param starId Unique ID for the new star.
 * @param nearStarIds Other stars linked to this solar system by wormholes.
 *                    No near stars indicates centre of new galaxy.
 *                    There is no limit to number of stars per galaxy.
 *                    Stars cannot be near themselves
 *                    Galaxy must form planar graph with max degrees 4.
 * @param planetIds Planets in order from distance from star - attributes are randomly assigned.
 */
case class SolarSystemCreated(instanceId:InstanceId,
                              timeStamp:Long,
                              starId: StarId,
                              nearStarIds: List[StarId],
                              planetIds: List[PlanetId]) extends SolarSystemEvent

/**
 * Planet named.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param starId The planet's solar system.
 * @param planetId The planet being named.
 * @param name New name for the planet, unique within this solar system.
 */
case class PlanetNamed(instanceId:InstanceId, timeStamp:Long, starId:StarId, planetId:PlanetId, name:ShortAlphanumericName) extends SolarSystemEvent

/**
 * Star named.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param starId The star being named.
 * @param name New name for the star. Eventually consistent across solar systems.
 */
case class StarNamed(instanceId:InstanceId, timeStamp:Long, starId:StarId, name:ShortAlphabeticName) extends SolarSystemEvent

/**
 * Star name duplicate renamed.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param starId The star which is having its name changed to resolve the duplicate name inconsistency.
 * @param conflictingStarId The conflicting star which is keeping its name.
 * @param name New name for the star.
 */
case class StarNameDuplicateRenamed(instanceId:InstanceId,
                                    timeStamp:Long,
                                    starId:StarId,
                                    conflictingStarId:StarId,
                                    name:ShortAlphabeticName) extends SolarSystemEvent
