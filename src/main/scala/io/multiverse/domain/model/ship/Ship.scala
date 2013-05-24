package io.multiverse.domain.model.ship

import io.multiverse.domain.shared.{AggregateRoot, ExplicitAggregateFactory, Entity}
import io.multiverse.domain.model.instance.{UserSignedIn, InstanceId}
import io.multiverse.domain.model.solarSystem.{StarId, SolarSystem, PlanetId}
import io.multiverse.domain.model.planetIndustry.{ShipBuildCommissioned => ShipBuildCommissionedAtPlanet}
import io.multiverse.domain.model.planetOwnership.PlanetOwnership

/**
 * Ship.
 * @param changes Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 */
case class Ship private(changes: List[ShipEvent], id: ShipId)
  extends Entity[ShipId] with AggregateRoot[Ship, ShipEvent] {

  /**
   * Clears the backlog of uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Ship = copy(changes = Nil)

  /**
   * Orders the ship to attack the specified non-friendly ship.
   * @param shipId Non-friendly ship to attack.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to attack given ship.
   */
  def attack(shipId:ShipId,
             instanceId:InstanceId,
             timeStamp:Long): Ship = {
    require(shipId != id, "Attacks must be against another ship.")
    applyEvent(ShipAttackOrdered(instanceId, timeStamp, id, shipId))
  }

  /**
   * Orders the ship to attack the specified non-friendly planet.
   * @param planetId Non-friendly planet to attack.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to attack given planet. Updated planet ownership.
   */
  def attack(planetId:PlanetId,
             instanceId:InstanceId,
             timeStamp:Long): Ship =
    applyEvent(PlanetAttackOrdered(instanceId, timeStamp, id, planetId))

  /**
   * Orders the ship to colonize the specified vacant planet.
   * @param planetId Vacant planet to colonize.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to colonize given planet. Updated planet ownership.
   */
  def colonize(planetId:PlanetId,
               instanceId:InstanceId,
               timeStamp:Long): Ship =
    applyEvent(PlanetColonizationOrdered(instanceId, timeStamp, id, planetId))

  /**
   * Orders the ship to orbit the specified non-hostile planet.
   * @param planetId Non-hostile planet to orbit.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to orbit given planet.
   */
  def orbit(planetId:PlanetId,
            instanceId:InstanceId,
            timeStamp:Long): Ship =
    applyEvent(PlanetOrbitOrdered(instanceId, timeStamp, id, planetId))

  /**
   * Orders the ship to an offset in a particular solar system.
   * @param starId Solar system the offset is relative to.
   * @param offset Coordinates the ship should move to.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to given coordinates.
   */
  def moveTo(starId:StarId, offset:StarOffset, instanceId:InstanceId, timeStamp:Long): Ship = {
    applyEvent(ShipCoordinatesOrdered(instanceId, timeStamp, id, starId, offset))
  }

  /**
   * Orders the ship to orbit the entry wormhole in a particular solar system.
   * @param starId Solar system to enter.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Ship on-course to given solar system.
   */
  def moveTo(starId:StarId, instanceId:InstanceId, timeStamp:Long): Ship =
    applyEvent(SolarSystemEntryOrdered(instanceId, timeStamp, id, starId))

  /**
   * Orders the ship to do nothing.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Halted ship.
   */
  def halt(instanceId:InstanceId, timeStamp:Long): Ship =
    applyEvent(ShipHaltOrdered(instanceId, timeStamp, id))

  /**
   * Orders the ship to self-destruct.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Decommissioned ship.
   */
  def decommission(instanceId:InstanceId, timeStamp:Long): Ship = {
    val decommissioned = ShipDecommissioned(instanceId, timeStamp, id)
    applyEvent(decommissioned).destroy(decommissioned, instanceId, timeStamp)
  }

  /**
   * Immediately ends the ship's existence.
   * @param destructionEvent Event resulting in the ship's destruction.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Destroyed ship.
   */
  def destroy(destructionEvent:ShipEvent, instanceId:InstanceId, timeStamp:Long): Ship = {
    applyEvent(ShipDestroyed(instanceId, timeStamp, id, destructionEvent))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Ship with event appended and new state applied.
   */
  def applyEvent(event: ShipEvent):Ship = {
    event match {
      case event:PlanetAttackOrdered => copy(changes = changes :+ event)
      case event:PlanetColonizationOrdered => copy(changes = changes :+ event)
      case event:PlanetOrbitOrdered => copy(changes = changes :+ event)
      case event:ShipAttackOrdered => copy(changes = changes :+ event)
      case event:ShipBuilt => copy(changes = changes :+ event)
      case event:ShipCoordinatesOrdered => copy(changes = changes :+ event)
      case event:ShipDecommissioned => copy(changes = changes :+ event)
      case event:ShipDestroyed => copy(changes = changes :+ event)
      case event:ShipHaltOrdered => copy(changes = changes :+ event)
      case event:SolarSystemEntryOrdered => copy(changes = changes :+ event)
      case event:ShipEvent => unhandled(event)
    }
  }
}

/**
 * Ship factory.
 */
object Ship extends ExplicitAggregateFactory[Ship, ShipEvent] {
  /**
   * Maximum absolute value of a solar system coordinate relative to its star.
   */
  val maxAbsCoordinate = 1000

  /**
   * Creates a new solar system.
   * @param canonicalEvent Original build event.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship.
   */
  def finalizeBuild(canonicalEvent:ShipBuildCommissionedAtPlanet, instanceId:InstanceId, timeStamp:Long):Ship =
    applyEvent(ShipBuilt(instanceId, timeStamp, canonicalEvent.shipId, canonicalEvent))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: ShipEvent):Ship = {
    event match {
      case event: ShipBuilt => Ship(Nil :+ event, event.shipId)
      case event: ShipEvent => unhandled(event)
    }
  }
}
