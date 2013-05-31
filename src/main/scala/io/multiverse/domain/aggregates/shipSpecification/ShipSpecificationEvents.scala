package io.multiverse.domain.aggregates.shipSpecification

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.species.SpeciesId
import io.multiverse.domain.aggregates.shipAssets.ShipAssetsId
import io.multiverse.domain.aggregates.common.ShortAlphanumericName

/**
 * Ship specified.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipSpecificationId Unique ID for the new specification.
 * @param authorId Species who authored the specification.
 * @param shipAssetsId Binary assets associated with the ship specification.
 * @param size Size of the ship this specification defines.
 * @param name Specification name.
 * @param composition Composition of the specified ship type's component.
 */
case class ShipSpecified(instanceId:InstanceId,
                         timeStamp:Long,
                         shipSpecificationId:ShipSpecificationId,
                         authorId:SpeciesId,
                         shipAssetsId:ShipAssetsId,
                         size:ShipSize,
                         name:ShortAlphanumericName,
                         composition:ShipComposition) extends ShipSpecificationEvent
