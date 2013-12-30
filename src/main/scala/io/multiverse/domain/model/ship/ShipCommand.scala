package io.multiverse.domain.model.ship

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a ship.
 */
trait ShipCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Ship relative to the command.
   */
  val shipId: ShipId
}
