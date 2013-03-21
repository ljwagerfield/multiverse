package com.wagerfield.multiverse.domain.model.ship

import com.wagerfield.multiverse.domain.shared.{AggregateRoot, ValidatedEntityAggregateFactory, Entity}
import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.solarSystem.{StarId, SolarSystem, PlanetId}
import com.wagerfield.multiverse.domain.model.planetIndustry.{ShipBuildCommissioned => ShipBuildCommissionedAtPlanet}
import com.wagerfield.multiverse.domain.model.planetOwnership.PlanetOwnership

/**
 * Ship.
 * @param uncommittedEvents Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 */
case class Ship private(uncommittedEvents: List[ShipEvent],
                               id: ShipId)
  extends Entity[ShipId] with AggregateRoot[Ship, ShipEvent] {
  /**
   * Clears the backlog of uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Ship = copy(uncommittedEvents = Nil)

  /**
   * Orders the ship to attack the specified non-friendly ship.
   * @param shipId Non-friendly ship to attack.
   * @param planetSolarSystem Solar system the planet exists in.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to attack given ship.
   */
  def attack(shipId:ShipId,
             planetSolarSystem:SolarSystem,
             instanceId:InstanceId,
             timeStamp:Long): Ship = {
    val event = ShipAttackOrdered(instanceId, timeStamp, id, shipId)
    applyEvent(event)
  }

  /**
   * Orders the ship to attack the specified non-friendly planet.
   * @param planet Non-friendly planet to attack.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to attack given planet. Updated planet ownership.
   */
  def attack(planet:PlanetOwnership,
             instanceId:InstanceId,
             timeStamp:Long): (Ship, PlanetOwnership) = {
    val event = PlanetAttackOrdered(instanceId, timeStamp, id, planet.planetId)
    (applyEvent(event),
      planet.shipInboundForOwnership(event, instanceId, timeStamp))
  }

  /**
   * Orders the ship to colonize the specified vacant planet.
   * @param planet Vacant planet to colonize.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to colonize given planet. Updated planet ownership.
   */
  def colonize(planet:PlanetOwnership,
               instanceId:InstanceId,
               timeStamp:Long): (Ship, PlanetOwnership) = {
    val event = PlanetColonizationOrdered(instanceId, timeStamp, id, planet.planetId)
    (applyEvent(event),
      planet.shipInboundForOwnership(event, instanceId, timeStamp))
  }

  /**
   * Orders the ship to orbit the specified non-hostile planet.
   * @param planetId Non-hostile planet to orbit.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to orbit given planet.
   */
  def orbit(planetId:PlanetId,
            instanceId:InstanceId,
            timeStamp:Long): Ship = {
    val event = PlanetOrbitOrdered(instanceId, timeStamp, id, planetId)
    applyEvent(event)
  }

  /**
   * Orders the ship to an offset in a particular solar system.
   * @param starId Solar system the offset is relative to.
   * @param xOffset X offset from the solar system's center.
   * @param yOffset Y offset from the solar system's center.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to given coordinates.
   */
  def moveTo(starId:StarId, xOffset:Int, yOffset:Int, instanceId:InstanceId, timeStamp:Long): Ship = {
    applyEvent(ShipCoordinatesOrdered(instanceId, timeStamp, id, starId, xOffset, yOffset))
  }

  /**
   * Orders the ship to orbit the entry wormhole in a particular solar system.
   * @param starId Solar system to enter.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to given solar system.
   */
  def moveTo(starId:StarId, instanceId:InstanceId, timeStamp:Long): Ship = {
    applyEvent(SolarSystemEntryOrdered(instanceId, timeStamp, id, starId))
  }

  /**
   * Orders the ship to do nothing.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship halted.
   */
  def halt(instanceId:InstanceId, timeStamp:Long): Ship = {
    applyEvent(ShipHaltOrdered(instanceId, timeStamp, id))
  }

  /**
   * Orders the ship to self-destruct.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship halted.
   */
  def decommission(instanceId:InstanceId, timeStamp:Long): Ship = {
    applyEvent(ShipDecommissioned(instanceId, timeStamp, id))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Ship with event appended and new state applied.
   */
  def applyEvent(event: ShipEvent):Ship = {
    event match {
      case event: ShipEvent => unhandled(event)
    }
  }
}

/**
 * Ship factory.
 */
object Ship extends ValidatedEntityAggregateFactory[Ship, ShipEvent] {
  /**
   * Creates a new solar system.
   * @param canonicalEvent Original build event.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship.
   */
  def build(canonicalEvent:ShipBuildCommissionedAtPlanet, instanceId:InstanceId, timeStamp:Long):Ship =
    applyEvent(ShipBuildCommissioned(instanceId, timeStamp, canonicalEvent.shipId, canonicalEvent))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: ShipEvent):Ship = {
    event match {
      case event: ShipBuildCommissioned => Ship(Nil :+ event, event.shipId)
      case event: ShipEvent => unhandled(event)
    }
  }
}
