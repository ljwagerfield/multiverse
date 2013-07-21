package io.multiverse.domain.aggregates.planetOwnership

import io.multiverse.domain.aggregates.ship.PlanetColonizationOrdered
import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.solarSystem.PlanetId
import io.multiverse.domain.aggregates.common.{ImplicitAggregateFactory, AggregateRoot}

/**
 * Industry for a particular planet.
 * @param changes Events pending commitment.
 * @param planetId Planet whose industry this is.
 */
case class PlanetOwnership private(changes: List[PlanetOwnershipEvent], planetId:PlanetId, isVacant: Boolean)
  extends AggregateRoot[PlanetOwnership, PlanetOwnershipEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: PlanetOwnership = copy(changes = Nil)

  /**
   * Sets the given species as the new owners of this planet. Any existing species are overthrown.
   * @param colonizationOrder Order which invoked the inbound ship.
   * @param instanceId Instance invoking the command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate with inbound ship.
   */
  def colonize(colonizationOrder: PlanetColonizationOrdered, instanceId:InstanceId, timestamp:Long):PlanetOwnership = {
    require(colonizationOrder.planetId == planetId, "Ship colonization order must correspond to the planet represented by this aggregate.")
    apply(PlanetColonized(planetId, colonizationOrder, instanceId, timestamp))
  }

  /**
   * Abandons the current inhabitants from the planet.
   * @param instanceId Instance invoking this command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate with no inhabitants.
   */
  def abandon(instanceId:InstanceId, timestamp:Long): PlanetOwnership = {
    if (isVacant) {
      this
    }
    else {
      apply(PlanetAbandoned(planetId, instanceId, timestamp))
    }
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def apply(event: PlanetOwnershipEvent): PlanetOwnership = {
    event match {
      case event:PlanetColonized => copy(changes = changes :+ event, isVacant = false)
      case event:PlanetAbandoned => copy(changes = changes :+ event, isVacant = true)
      case event:PlanetOwnershipEvent => unhandledEvent(event)
    }
  }
}

/**
 * Planet ownership factory.
 */
object PlanetOwnership extends ImplicitAggregateFactory[PlanetOwnership, PlanetOwnershipEvent] {
  /**
   * Initializes ownership for the given planet.
   * @param planetId Planet whose ownership is to be initialised.
   * @return Aggregate representing the initialized planet industry.
   */
  def init(planetId:PlanetId):PlanetOwnership =
    PlanetOwnership(Nil, planetId, isVacant = true)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent: PlanetOwnershipEvent): PlanetOwnership = init(initialEvent.planetId)
}
