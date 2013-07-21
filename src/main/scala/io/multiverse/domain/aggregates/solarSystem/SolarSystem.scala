package io.multiverse.domain.aggregates.solarSystem

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.{Entity, ExplicitAggregateFactory, ShortAlphabeticName, AggregateRoot, ShortAlphanumericName}

/**
 * Solar system composing a star and planets.
 * @param changes Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 * @param namedPlanets Explicitly named planets.
 */
case class SolarSystem private(changes: List[SolarSystemEvent],
                               id: StarId,
                               namedPlanets:Map[ShortAlphanumericName, PlanetId])
  extends Entity[StarId] with AggregateRoot[SolarSystem, SolarSystemEvent] {
  /**
   * Clears the backlog of uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: SolarSystem = copy(changes = Nil)

  /**
   * Assigns a name to the star which is eventually unique within the universe.
   * @param name Unique star name within the universe.
   * @param instanceId Instance invoking the command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Solar system with star renamed.
   */
  def nameStar(name:ShortAlphabeticName, instanceId:InstanceId, timestamp:Long):SolarSystem =
    apply(StarNamed(id, name, instanceId, timestamp))

  /**
   * Assigns a name to the planet which is unique within the solar system.
   * @param planetId Planet to name.
   * @param name Unique name within the solar system.
   * @param instanceId Instance invoking the command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Solar system with planet renamed.
   */
  def namePlanet(planetId:PlanetId, name:ShortAlphanumericName, instanceId:InstanceId, timestamp:Long):SolarSystem = {
    require(namedPlanets.get(name).forall(_ == planetId), "Planet name must be unique within this solar system.")
    apply(PlanetNamed(id, planetId, name, instanceId, timestamp))
  }

  /**
   * Resolves a duplicate star name inconsistency.
   * @param conflictingStarId Conflicting star which is keeping its name.
   * @param newName New name for this star.
   * @param instanceId Instance invoking the command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Solar system with resolved star name.
   */
  def resolveDuplicateStarName(conflictingStarId:StarId, newName:ShortAlphabeticName, instanceId:InstanceId, timestamp:Long):SolarSystem =
    apply(StarNameDuplicateRenamed(id, conflictingStarId, newName, instanceId, timestamp))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Solar system with event appended and new state applied.
   */
  def apply(event: SolarSystemEvent):SolarSystem = {
    event match {
      case event: StarNamed => copy(changes = changes :+ event)
      case event: PlanetNamed => copy(changes = changes :+ event, namedPlanets = namedPlanets + (event.name -> event.planetId))
      case event: StarNameDuplicateRenamed => copy(changes =  changes :+ event)
      case event: SolarSystemEvent => unhandledEvent(event)
    }
  }
}

/**
 * Solar system factory.
 */
object SolarSystem extends ExplicitAggregateFactory[SolarSystem, SolarSystemEvent] {
  /**
   * Creates a new solar system.
   * @param starId Unique ID for the new star.
   * @param nearStarIds Other stars linked to this one via wormholes.
   * @param planetIds Unique ID for each planet orbiting the star.
   * @param instanceId Instance invoking the command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New solar system.
   */
  def create(starId: StarId, nearStarIds: Set[StarId], planetIds: Set[PlanetId], instanceId:InstanceId, timestamp:Long):SolarSystem = {
    require(nearStarIds.forall(_ != starId), "Near stars must reference other solar systems.")
    require(nearStarIds.size < 5, "Stars must be near to fewer than 5 stars.")
    require(planetIds.size > 1 && planetIds.size < 7, "Solar systems must contain between 2 and 6 planets, inclusively.")
    apply(SolarSystemCreated(starId, nearStarIds, planetIds, instanceId, timestamp))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Solar system with event appended and new state applied.
   */
  def apply(event: SolarSystemEvent):SolarSystem = {
    event match {
      case event: SolarSystemCreated => SolarSystem(Nil :+ event, event.starId, Map())
      case event: SolarSystemEvent => unhandledEvent(event)
    }
  }
}
