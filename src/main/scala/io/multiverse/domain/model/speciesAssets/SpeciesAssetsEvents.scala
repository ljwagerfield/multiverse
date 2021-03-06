package io.multiverse.domain.model.speciesAssets

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.values.Hash

/**
 * Species assets defined.
 * @param speciesAssetsId Unique ID for the new species assets.
 * @param hash References the binary assets.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SpeciesAssetsDefined(speciesAssetsId: SpeciesAssetsId, hash: Hash, instanceId:InstanceId, timestamp:Long) extends SpeciesAssetsEvent
