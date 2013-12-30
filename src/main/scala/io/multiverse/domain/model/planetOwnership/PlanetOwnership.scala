package io.multiverse.domain.model.planetOwnership

import io.multiverse.domain.model.common.{Aggregate, AggregateRootBase, ImplicitAggregateFactory}
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Ownership for a particular planet.
 * @param changes Events pending commitment.
 * @param planetId Planet whose ownership this is.
 */
case class PlanetOwnership private(changes: List[PlanetOwnershipEvent], planetId:PlanetId, isVacant: Boolean)
  extends AggregateRootBase[PlanetOwnership, PlanetOwnershipEvent] with Aggregate[PlanetOwnership] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[PlanetOwnership] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[PlanetOwnership#Event, Aggregate[PlanetOwnership]] = {
    case event:PlanetColonized => copy(changes = changes :+ event, isVacant = false)
    case event:PlanetAbandoned => copy(changes = changes :+ event, isVacant = true)
  }
}

/**
 * Planet ownership factory.
 */
object PlanetOwnership extends ImplicitAggregateFactory[PlanetOwnership] {

  /**
   * Initializes ownership for the given planet.
   * @param planetId Planet whose ownership is to be initialised.
   * @return Aggregate representing the initialized planet industry.
   */
  def apply(planetId:PlanetId): Aggregate[PlanetOwnership] =
    PlanetOwnership(Nil, planetId, isVacant = true)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent: PlanetOwnershipEvent): Aggregate[PlanetOwnership] =
    PlanetOwnership(initialEvent.planetId)
}
