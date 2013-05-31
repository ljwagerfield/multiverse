package io.multiverse.domain.aggregates.planetIndustry

import io.multiverse.domain.aggregates.shipSpecification.ShipSpecificationId
import io.multiverse.domain.aggregates.ship.ShipId
import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.solarSystem.PlanetId
import io.multiverse.domain.aggregates.common.{ImplicitAggregateFactory, AggregateRoot}

/**
 * Industry for a particular planet.
 * @param changes Events pending commitment.
 * @param planetId Planet whose industry this is.
 */
case class PlanetIndustry private(changes: List[PlanetIndustryEvent], planetId:PlanetId)
  extends AggregateRoot[PlanetIndustry, PlanetIndustryEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: PlanetIndustry = copy(changes = Nil)

  /**
   * Commissions a new ship build.
   * @param shipSpecificationId Specification the new ship will be based on.
   * @param shipId Unique ID for the new ship.
   * @param instanceId Instance invoking this command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate with new ship build commissioned.
   */
  def buildShip(shipSpecificationId:ShipSpecificationId, shipId:ShipId, instanceId:InstanceId, timeStamp:Long): PlanetIndustry =
    applyEvent(ShipBuildCommissioned(instanceId, timeStamp, planetId, shipSpecificationId, shipId))


  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: PlanetIndustryEvent): PlanetIndustry = {
    event match {
      case event:ShipBuildCommissioned => copy(changes = changes :+ event)
      case event:PlanetIndustryEvent => unhandled(event)
    }
  }
}

/**
 * Planet industry factory.
 */
object PlanetIndustry extends ImplicitAggregateFactory[PlanetIndustry, PlanetIndustryEvent] {
  /**
   * Initializes industry for the given planet.
   * @param planetId Planet whose industry this is.
   * @return Aggregate representing the initialized planet industry.
   */
  def init(planetId:PlanetId):PlanetIndustry =
    PlanetIndustry(Nil, planetId)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return Prototypical instance.
   */
  def getPrototype(initialEvent: PlanetIndustryEvent): PlanetIndustry = init(initialEvent.planetId)
}
