package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.species.SpeciesId
import io.multiverse.domain.model.shipAssets.ShipAssetsId
import io.multiverse.domain.model.common.{ExplicitAggregateFactory, AggregateRoot}
import io.multiverse.domain.model.common.values.ShortAlphanumericName

/**
 * Binary media assets for the background of a species flag.
 * @param changes Events pending commitment.
 */
case class ShipSpecification private(changes: List[ShipSpecificationEvent])
  extends AggregateRoot[ShipSpecification, ShipSpecificationEvent] {

  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: ShipSpecification = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag background vector with event appended and new state applied.
   */
  def apply(event: ShipSpecificationEvent): ShipSpecification = unhandledEvent(event)
}

/**
 * Ship specification factory.
 */
object ShipSpecification extends ExplicitAggregateFactory[ShipSpecification, ShipSpecificationEvent] {
  /**
   * Defines a new ship specification.
   * @param shipSpecificationId Unique ID for the new specification.
   * @param authorId Species who authored the specification.
   * @param shipAssetsId Binary assets associated with the ship specification.
   * @param size Size of the ship this specification defines.
   * @param name Specification name.
   * @param composition Composition of the specified ship type's component.
   * @param instanceId Instance the event occurred in.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship specification.
   */
  def define(shipSpecificationId:ShipSpecificationId,
             authorId:SpeciesId,
             shipAssetsId:ShipAssetsId,
             size:ShipSize,
             name:ShortAlphanumericName,
             composition:ShipComposition,
             instanceId:InstanceId,
             timestamp:Long):ShipSpecification = {
    // Note: specification aggregate could validate against size if individual components were represented by value objects.
    apply(ShipSpecified(
      shipSpecificationId,
      authorId,
      shipAssetsId,
      size,
      name,
      composition,
      instanceId,
      timestamp))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag background vector with event appended and new state applied.
   */
  def apply(event: ShipSpecificationEvent):ShipSpecification = {
    event match {
      case event: ShipSpecified => ShipSpecification(Nil :+ event)
      case event: ShipSpecificationEvent => unhandledEvent(event)
    }
  }
}
