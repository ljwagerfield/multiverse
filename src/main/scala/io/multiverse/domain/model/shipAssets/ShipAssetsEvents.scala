package io.multiverse.domain.model.shipAssets

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.shipSpecification.ShipSize
import io.multiverse.domain.model.common.values.Hash

/**
 * Ship assets defined: ID and size form composite key.
 * @param shipAssetsId Unique ID for the new ship assets.
 * @param size Ship size.
 * @param hash References the binary assets.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipAssetsDefined(shipAssetsId:ShipAssetsId, size:ShipSize, hash:Hash, instanceId:InstanceId, timestamp:Long) extends ShipAssetsEvent
