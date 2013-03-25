package com.wagerfield.multiverse.domain.model.planetOwnership

import com.wagerfield.multiverse.domain.shared.{ValidatedValueObjectAggregateFactory, AggregateRoot}
import com.wagerfield.multiverse.domain.model.ship.ShipEvent
import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId

/**
 * Industry for a particular planet.
 * @param uncommittedEvents Events pending commitment.
 * @param planetId Planet whose industry this is.
 */
case class PlanetOwnership private(uncommittedEvents: List[PlanetOwnershipEvent],
                            planetId:PlanetId,
                            isColonized:Boolean)
  extends AggregateRoot[PlanetOwnership, PlanetOwnershipEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: PlanetOwnership = copy(uncommittedEvents = Nil)

  /**
   * Informs the aggregate of a potential change in planet ownership.
   * @param colonizationOrder Order which invoked the inbound ship.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate with inbound ship.
   */
  def colonize(colonizationOrder: ShipEvent, instanceId:InstanceId, timeStamp:Long):PlanetOwnership =
    applyEvent(PlanetColonized(instanceId, timeStamp, planetId, colonizationOrder))

  /**
   * Abandons the current inhabitants from the planet.
   * @param instanceId Instance invoking this command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate with no inhabitants.
   */
  def abandon(instanceId:InstanceId, timeStamp:Long): PlanetOwnership = {
    assert(isColonized, "Abandoning a planet requires it to be colonized.")
    applyEvent(PlanetAbandoned(instanceId, timeStamp, planetId))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: PlanetOwnershipEvent): PlanetOwnership = {
    event match {
      case event:PlanetColonized => copy(uncommittedEvents = uncommittedEvents :+ event, isColonized = true)
      case event:PlanetAbandoned => copy(uncommittedEvents = uncommittedEvents :+ event, isColonized = false)
      case event:PlanetOwnershipEvent => unhandled(event)
    }
  }
}

/**
 * Planet ownership factory.
 */
object PlanetOwnership extends ValidatedValueObjectAggregateFactory[PlanetOwnership, PlanetOwnershipEvent] {
  /**
   * Initializes ownership for the given planet.
   * @param planetId Planet whose ownership is to be initialised.
   * @return Aggregate representing the initialized planet industry.
   */
  def init(planetId:PlanetId):PlanetOwnership =
    PlanetOwnership(Nil, planetId, false)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent: PlanetOwnershipEvent): PlanetOwnership = init(initialEvent.planetId)
}
