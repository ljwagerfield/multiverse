package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.shared.{AggregateRoot, AggregateFactory, Entity}

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * A solar system composes a star and planets.
 */
case class SolarSystem private(uncommittedEvents: List[SolarSystemEvent], id: StarId) extends Entity[StarId] with AggregateRoot[SolarSystem, SolarSystemEvent] {
	/**
	 * Processes uncommitted events.
	 * @return Aggregate with no uncommitted events.
	 */
	def markCommitted: SolarSystem = ???

	def namePlanet(planetId: PlanetId, name: String) = applyEvent(PlanetNamed(id, planetId, name))

	/**
	 * Applies the given event as the head of the returned object's state.
	 * @return
	 */
	def applyEvent(event: SolarSystemEvent) = {
		copy()
	}
}

object SolarSystem extends AggregateFactory[SolarSystem, SolarSystemEvent] {
	def create(starId: StarId, nearStarIds: List[StarId], planetIds: List[PlanetId]) = applyEvent(SolarSystemCreated(starId, nearStarIds, planetIds))

	def applyEvent(event: SolarSystemEvent) = {
		case event: SolarSystemCreated => SolarSystem(event :: Nil, event.starId)
		case event => unhandled(event)
	}
}
