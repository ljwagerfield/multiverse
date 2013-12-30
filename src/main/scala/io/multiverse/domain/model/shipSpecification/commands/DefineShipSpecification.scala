package io.multiverse.domain.model.shipSpecification.commands

import io.multiverse.domain.model.common.Aggregate
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.values.ShortAlphanumericName
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.shipAssets.ShipAssetsId
import io.multiverse.domain.model.shipSpecification.{ShipSpecified, ShipSpecification, ShipSpecificationCommand, ShipComposition, ShipSize, ShipSpecificationId}
import io.multiverse.domain.model.species.SpeciesId

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
 */
case class DefineShipSpecification(shipSpecificationId:ShipSpecificationId,
                  authorId:SpeciesId,
                  shipAssetsId:ShipAssetsId,
                  size:ShipSize,
                  name:ShortAlphanumericName,
                  composition:ShipComposition,
                  instanceId:InstanceId,
                  timestamp:Long)
  extends ShipSpecificationCommand with HeadCommand[ShipSpecification] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  lazy val evaluation: Aggregate[ShipSpecification] =
    ShipSpecification(ShipSpecified(
      shipSpecificationId,
      authorId,
      shipAssetsId,
      size,
      name,
      composition,
      instanceId,
      timestamp))
}
