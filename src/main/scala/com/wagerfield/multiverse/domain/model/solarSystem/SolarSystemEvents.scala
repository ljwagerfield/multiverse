package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}

/**
 * Solar system created: no near stars indicates centre of new galaxy; no limit to number of stars per galaxy; stars cannot be near themselves; galaxy must form planar graph with max degrees 4; planets in order from distance from star; planet attributes dynamically generated. 
 */
case class SolarSystemCreated(starId: StarId, nearStarIds: List[StarId], planetIds: List[PlanetId]) extends SolarSystemEvent

/**
 * Planet named by species: name is unique within aggregate.
 */
case class PlanetNamed(starId: StarId, planetId: PlanetId, name: String) extends SolarSystemEvent

/**
 * Star named by species: name eventually unique across aggregates.
 */
case class StarNamedB(starId: StarId, name: String) extends SolarSystemEvent

/**
 * Star name duplicate renamed.
 */
case class StarNameDuplicateRenamed(starId: StarId, conflictingStarId: StarId, name: String) extends SolarSystemEvent

/**
 * Ship bound for solar system: ping event type; ignore if last journey for ship does not match one provided.
 */
case class ShipBoundForSolarSystem(starId: StarId, shipId: ShipId, journeyEvent: ShipEvent) extends SolarSystemEvent