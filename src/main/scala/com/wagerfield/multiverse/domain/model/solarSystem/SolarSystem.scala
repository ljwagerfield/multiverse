package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.shared.{AggregateRoot, AggregateFactory, Entity}
import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * A solar system composes a star and planets.
 */
case class SolarSystem private(uncommittedEvents: List[SolarSystemEvent], instanceId:InstanceId, id: StarId, name:String)
	extends Entity[StarId] with AggregateRoot[SolarSystem, SolarSystemEvent] {
	/**
	 * Clears the backlog of uncommitted events.
	 * @return Aggregate with no uncommitted events.
	 */
	def markCommitted: SolarSystem = copy(uncommittedEvents = Nil)

	/**
	 * Assigns a name to the planet which is unique within the solar system.
	 * @param planetId The planet to name.
	 * @param name The unique name within the solar system.
	 * @return Aggregate with planet renamed.
	 */
	def namePlanet(planetId: PlanetId, name: String) = {
		// TODO: Validate uniqueness here.
		applyEvent(PlanetNamed(instanceId, id, planetId, name))
	}

	/**
	 * Assigns a name to the star which is eventually unique within the universe.
	 * @param name The unique name within the solar system.
	 * @return Aggregate with planet renamed.
	 */
	def nameStar(name: String) = applyEvent(StarNamed(instanceId, id, name))

	/**
	 * Informs the solar system of an inbound ship.
	 * @param shipId The inbound ship.
	 * @param journeyEvent The latest journey event for the ship which set its course for this solar system.
	 * @return Aggregate with inbound ship.
	 */
	def shipInbound(shipId: ShipId, journeyEvent: ShipEvent) = applyEvent(ShipBoundForSolarSystem(instanceId, id, shipId, journeyEvent))

	/**
	 * Applies the given event as the head of the returned object's state.
	 * @return Aggregate with event applied.
	 */
	def applyEvent(event: SolarSystemEvent) = {
		case event: StarNamed => copy(uncommittedEvents = event.uncommittedEvents :: event, name = event.name)
		case event => unhandled(event)
	}
}

object SolarSystem extends AggregateFactory[SolarSystem, SolarSystemEvent] {
	def create(instanceId:InstanceId, starId: StarId, nearStarIds: List[StarId], planetIds: List[PlanetId]) = applyEvent(SolarSystemCreated(instanceId, starId, nearStarIds, planetIds))

	def applyEvent(event: SolarSystemEvent) = {
		case event: SolarSystemCreated => SolarSystem(event :: Nil, event.instanceId, event.starId, null)
		case event => unhandled(event)
	}
}
