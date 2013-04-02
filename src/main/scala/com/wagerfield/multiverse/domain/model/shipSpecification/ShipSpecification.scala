package com.wagerfield.multiverse.domain.model.shipSpecification

import com.wagerfield.multiverse.domain.shared.{ValidatedEntityAggregateFactory, AggregateRoot}
import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.species.SpeciesId
import com.wagerfield.multiverse.domain.model.shipAssets.ShipAssetsId

/**
 * Binary media assets for the background of a species flag.
 * @param uncommittedEvents Events pending commitment.
 */
case class ShipSpecification private(uncommittedEvents: List[ShipSpecificationEvent])
  extends AggregateRoot[ShipSpecification, ShipSpecificationEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: ShipSpecification = copy(uncommittedEvents = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag background vector with event appended and new state applied.
   */
  def applyEvent(event: ShipSpecificationEvent): ShipSpecification = unhandled(event)
}

/**
 * Ship specification factory.
 */
object ShipSpecification extends ValidatedEntityAggregateFactory[ShipSpecification, ShipSpecificationEvent] {
  /**
   * Defines a new ship specification.
   * @param shipSpecificationId Unique ID for the new specification.
   * @param authorId Species who authored the specification.
   * @param shipAssetsId Binary assets associated with the ship specification.
   * @param size Size of the ship this specification defines.
   * @param name Specification name.
   * @param composition Composition of the specified ship type's component.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship specification.
   */
  def define(shipSpecificationId:ShipSpecificationId,
             authorId:SpeciesId,
             shipAssetsId:ShipAssetsId,
             size:ShipSize,
             name:String,
             composition:ShipComposition,
             instanceId:InstanceId,
             timeStamp:Long):ShipSpecification = {
    require(name.matches("([a-z][A-Z][0-9]){1,15}"), "Name must be between 1 and 15 characters containing only alphanumeric characters.")
    // Note: specification aggregate could validate against size if individual components were represented by value objects.
    applyEvent(ShipSpecified(instanceId,
      timeStamp,
      shipSpecificationId,
      authorId,
      shipAssetsId,
      size,
      name,
      composition))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag background vector with event appended and new state applied.
   */
  def applyEvent(event: ShipSpecificationEvent):ShipSpecification = {
    event match {
      case event: ShipSpecified => ShipSpecification(Nil :+ event)
      case event: ShipSpecificationEvent => unhandled(event)
    }
  }
}
