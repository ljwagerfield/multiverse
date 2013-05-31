package io.multiverse.domain.aggregates.shipAssets

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.shipSpecification.ShipSize
import io.multiverse.domain.aggregates.common.Hash

/**
 * Ship assets defined: ID and size form composite key.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipAssetsId Unique ID for the new ship assets.
 * @param size Ship size.
 * @param hash References the binary assets.
 */
case class ShipAssetsDefined(instanceId:InstanceId, timeStamp:Long, shipAssetsId:ShipAssetsId, size:ShipSize, hash:Hash) extends ShipAssetsEvent
