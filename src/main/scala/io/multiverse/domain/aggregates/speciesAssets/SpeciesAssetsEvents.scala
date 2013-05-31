package io.multiverse.domain.aggregates.speciesAssets

import io.multiverse.domain.aggregates.common.Hash
import io.multiverse.domain.aggregates.instance.InstanceId

/**
 * Species assets defined.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param speciesAssetsId Unique ID for the new species assets.
 * @param hash References the binary assets.
 */
case class SpeciesAssetsDefined(instanceId:InstanceId, timeStamp:Long, speciesAssetsId:SpeciesAssetsId, hash:Hash) extends SpeciesAssetsEvent
