package io.multiverse.domain.model.shipResearch

import io.multiverse.domain.shared.{ValidatedValueObjectAggregateFactory, AggregateRoot}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.species.SpeciesId

/**
 * Ship-specific research for a given species.
 * @param changes Events pending commitment.
 * @param speciesId Species whose ship research this is.
 */
case class ShipResearch private(changes: List[ShipResearchEvent], speciesId:SpeciesId)
  extends AggregateRoot[ShipResearch, ShipResearchEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: ShipResearch = copy(changes = Nil)

  /**
   * Focuses ship research for the species.
   * @param components Component research focus.
   * @param production Production research focus.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship research.
   */
  def focus(components:ComponentResearchFocus,
            production:ProductionResearchFocus,
            instanceId:InstanceId,
            timeStamp:Long): ShipResearch =
    applyEvent(ShipResearchFocused(instanceId,
      timeStamp,
      speciesId,
      components,
      production))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag background vector with event appended and new state applied.
   */
  def applyEvent(event: ShipResearchEvent): ShipResearch = {
    event match {
      case event: ShipResearchFocused => copy(changes = changes :+ event)
      case event: ShipResearchEvent => unhandled(event)
    }
  }
}

/**
 * Ship research factory.
 */
object ShipResearch extends ValidatedValueObjectAggregateFactory[ShipResearch, ShipResearchEvent] {
  /**
   * Initializes ship research for the given species.
   * @param speciesId Species whose ship research this is.
   * @return Aggregate representing the initialized ship research.
   */
  def init(speciesId:SpeciesId):ShipResearch =
    ShipResearch(Nil, speciesId)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return Prototypical instance.
   */
  def getPrototype(initialEvent: ShipResearchEvent): ShipResearch = init(initialEvent.speciesId)
}
