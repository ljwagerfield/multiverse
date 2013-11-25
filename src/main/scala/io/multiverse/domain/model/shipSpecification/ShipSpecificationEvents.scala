package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.species.SpeciesId
import io.multiverse.domain.model.shipAssets.ShipAssetsId
import io.multiverse.domain.model.common.values.ShortAlphanumericName

/**
 * Ship specified.
 * @param shipSpecificationId Unique ID for the new specification.
 * @param authorId Species who authored the specification.
 * @param shipAssetsId Binary assets associated with the ship specification.
 * @param size Size of the ship this specification defines.
 * @param name Specification name.
 * @param composition Composition of the specified ship type's component.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipSpecified(shipSpecificationId:ShipSpecificationId,
                         authorId:SpeciesId,
                         shipAssetsId:ShipAssetsId,
                         size:ShipSize,
                         name:ShortAlphanumericName,
                         composition:ShipComposition,
                         instanceId:InstanceId,
                         timestamp:Long) extends ShipSpecificationEvent
