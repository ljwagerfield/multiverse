package io.multiverse.domain.model.shipAssets

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to the binary assets for a ship.
 */
trait ShipAssetsCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Ship assets relative to the command.
   */
  val shipAssetsId: ShipAssetsId
}
