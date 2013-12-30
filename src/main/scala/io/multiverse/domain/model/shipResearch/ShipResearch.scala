package io.multiverse.domain.model.shipResearch

import io.multiverse.domain.model.common.{Aggregate, AggregateRootBase, ImplicitAggregateFactory}
import io.multiverse.domain.model.species.SpeciesId

/**
 * Ship-specific research for a given species.
 * @param changes Events pending commitment.
 * @param speciesId Species whose ship research this is.
 */
case class ShipResearch private(changes: List[ShipResearchEvent], speciesId:SpeciesId)
  extends AggregateRootBase[ShipResearch, ShipResearchEvent] with Aggregate[ShipResearch] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[ShipResearch] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[ShipResearch#Event, Aggregate[ShipResearch]] = {
    case event: ShipResearchFocused => copy(changes = changes :+ event)
  }
}

/**
 * Ship research factory.
 */
object ShipResearch extends ImplicitAggregateFactory[ShipResearch] {

  /**
   * Initializes ship research for the given species.
   * @param speciesId Species whose ship research this is.
   * @return Aggregate representing the initialized ship research.
   */
  def apply(speciesId: SpeciesId): ShipResearch =
    ShipResearch(Nil, speciesId)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return Prototypical instance.
   */
  def getPrototype(initialEvent: ShipResearchEvent): ShipResearch =
    ShipResearch(initialEvent.speciesId)
}
