package io.multiverse.domain.aggregates.solarSystem

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.{ShortAlphabeticName, ShortAlphanumericName}

/**
 * Solar system created.
 * @param starId Unique ID for the new star.
 * @param nearStarIds Other stars linked to this solar system by wormholes.
 *                    No near stars indicates centre of new galaxy.
 *                    There is no limit to number of stars per galaxy.
 *                    Stars cannot be near themselves.
 *                    Galaxy must form planar graph with max degrees 4.
 * @param planetIds Planets in order from distance from star - attributes are randomly assigned.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SolarSystemCreated(starId:StarId,
                              nearStarIds:Set[StarId],
                              planetIds:Set[PlanetId],
                              instanceId:InstanceId,
                              timestamp:Long) extends SolarSystemEvent

/**
 * Planet named.
 * @param starId The planet's solar system.
 * @param planetId The planet being named.
 * @param name New name for the planet, unique within this solar system.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class PlanetNamed(starId:StarId, planetId:PlanetId, name:ShortAlphanumericName, instanceId:InstanceId, timestamp:Long) extends SolarSystemEvent

/**
 * Star named.
 * @param starId The star being named.
 * @param name New name for the star. Eventually consistent across solar systems.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class StarNamed(starId:StarId, name:ShortAlphabeticName, instanceId:InstanceId, timestamp:Long) extends SolarSystemEvent

/**
 * Star name duplicate renamed.
 * @param starId The star which is having its name changed to resolve the duplicate name inconsistency.
 * @param conflictingStarId The conflicting star which is keeping its name.
 * @param name New name for the star.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class StarNameDuplicateRenamed(starId:StarId,
                                    conflictingStarId:StarId,
                                    name:ShortAlphabeticName,
                                    instanceId:InstanceId,
                                    timestamp:Long) extends SolarSystemEvent
