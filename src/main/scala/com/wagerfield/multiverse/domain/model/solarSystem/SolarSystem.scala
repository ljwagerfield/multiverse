package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.shared.{AggregateRoot, ValidatedEntityAggregateFactory, Entity}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Solar system composing a star and planets.
 * @param uncommittedEvents Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 * @param namedPlanets Explicitly named planets.
 */
case class SolarSystem private(uncommittedEvents: List[SolarSystemEvent],
                               id: StarId,
                               namedPlanets:Map[PlanetId, String])
  extends Entity[StarId] with AggregateRoot[SolarSystem, SolarSystemEvent] {
  /**
   * Clears the backlog of uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: SolarSystem = copy(uncommittedEvents = Nil)

  /**
   * Assigns a name to the star which is eventually unique within the universe.
   * @param name Unique star name within the universe.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Solar system with star renamed.
   */
  def nameStar(name: String, instanceId:InstanceId, timeStamp:Long):SolarSystem =
    applyEvent(StarNamed(instanceId, timeStamp, id, name))

  /**
   * Assigns a name to the planet which is unique within the solar system.
   * @param planetId Planet to name.
   * @param name Unique name within the solar system.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Solar system with planet renamed.
   */
  def namePlanet(planetId: PlanetId, name: String, instanceId:InstanceId, timeStamp:Long):SolarSystem = {
    // TODO: Validate uniqueness here. Type system cannot be used as contract is dependant on context. DbC?
    applyEvent(PlanetNamed(instanceId, timeStamp, id, planetId, name))
  }

  /**
   * Resolves a duplicate star name inconsistency.
   * @param conflictingStarId Conflicting star which is keeping its name.
   * @param newName New name for this star.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Solar system with resolved star name.
   */
  def resolveDuplicateStarName(conflictingStarId:StarId, newName: String, instanceId:InstanceId, timeStamp:Long):SolarSystem =
    applyEvent(StarNameDuplicateRenamed(instanceId, timeStamp, id, conflictingStarId, newName))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Solar system with event appended and new state applied.
   */
  def applyEvent(event: SolarSystemEvent):SolarSystem = {
    event match {
      case event: StarNamed => copy(uncommittedEvents =  uncommittedEvents :+ event)
      // case event: PlanetNamed => copy(uncommittedEvents = uncommittedEvents :+ event, namedPlanets :+ )
      case event: StarNameDuplicateRenamed => copy(uncommittedEvents =  uncommittedEvents :+ event)
      case event: SolarSystemEvent => unhandled(event)
    }
  }
}

/**
 * Solar system factory.
 */
object SolarSystem extends ValidatedEntityAggregateFactory[SolarSystem, SolarSystemEvent] {
  /**
   * Creates a new solar system.
   * @param starId Unique ID for the new star.
   * @param nearStarIds Other stars linked to this one via wormholes.
   * @param planetIds Unique ID for each planet orbiting the star.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New solar system.
   */
  def create(starId: StarId, nearStarIds: List[StarId], planetIds: List[PlanetId], instanceId:InstanceId, timeStamp:Long):SolarSystem =
    applyEvent(SolarSystemCreated(instanceId, timeStamp, starId, nearStarIds, planetIds))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Solar system with event appended and new state applied.
   */
  def applyEvent(event: SolarSystemEvent):SolarSystem = {
    event match {
      case event: SolarSystemCreated => SolarSystem(Nil :+ event, event.starId, Map())
      case event: SolarSystemEvent => unhandled(event)
    }
  }
}
