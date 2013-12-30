package io.multiverse.domain.model.speciesAssets.commands

import io.multiverse.domain.model.speciesAssets.{SpeciesAssetsDefined, SpeciesAssets, SpeciesAssetsCommand, SpeciesAssetsId}
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.Aggregate

/**
 * Defines new binary assets for use in new species.
 * @param speciesAssetsId Unique ID for new species assets.
 * @param hash References the binary assets.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class DefineSpeciesAssets(speciesAssetsId:SpeciesAssetsId, hash:Hash, instanceId:InstanceId, timestamp:Long)
  extends SpeciesAssetsCommand with HeadCommand[SpeciesAssets] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  val evaluation: Aggregate[SpeciesAssets] =
    SpeciesAssets(SpeciesAssetsDefined(speciesAssetsId, hash, instanceId, timestamp))
}
