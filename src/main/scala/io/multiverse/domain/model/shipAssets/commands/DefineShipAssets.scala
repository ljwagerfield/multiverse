package io.multiverse.domain.model.shipAssets.commands

import io.multiverse.domain.model.common.Aggregate
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.shipAssets.{ShipAssets, ShipAssetsDefined, ShipAssetsCommand, ShipAssetsId}
import io.multiverse.domain.model.shipSpecification.ShipSize

/**
 * Defines new binary assets for use in ship specifications.
 * @param shipAssetsId Unique ID for new ship assets.
 * @param size Ship size.
 * @param hash References the binary assets.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class DefineShipAssets(shipAssetsId: ShipAssetsId, size: ShipSize, hash: Hash, instanceId: InstanceId, timestamp: Long)
  extends ShipAssetsCommand with HeadCommand[ShipAssets] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  lazy val evaluation: Aggregate[ShipAssets] =
    ShipAssets(ShipAssetsDefined(shipAssetsId, size, hash, instanceId, timestamp))
}
