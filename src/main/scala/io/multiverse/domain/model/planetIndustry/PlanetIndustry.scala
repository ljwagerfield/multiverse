package io.multiverse.domain.model.planetIndustry

import io.multiverse.domain.model.common.{Aggregate, AggregateRootBase, ImplicitAggregateFactory}
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Industry for a particular planet.
 * @param changes Events pending commitment.
 * @param planetId Planet whose industry this is.
 */
case class PlanetIndustry private(changes: List[PlanetIndustryEvent], planetId:PlanetId)
  extends AggregateRootBase[PlanetIndustry, PlanetIndustryEvent] with Aggregate[PlanetIndustry] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[PlanetIndustry] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[PlanetIndustry#Event, Aggregate[PlanetIndustry]] = {
    case event:ShipBuildCommissioned => copy(changes = changes :+ event)
  }
}

/**
 * Planet industry factory.
 */
object PlanetIndustry extends ImplicitAggregateFactory[PlanetIndustry] {

  /**
   * Initializes industry for the given planet.
   * @param planetId Planet whose industry this is.
   * @return Aggregate representing the initialized planet industry.
   */
  def apply(planetId: PlanetId): Aggregate[PlanetIndustry] =
    PlanetIndustry(Nil, planetId)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return Prototypical instance.
   */
  def getPrototype(initialEvent: PlanetIndustryEvent): Aggregate[PlanetIndustry] =
    PlanetIndustry(initialEvent.planetId)
}
