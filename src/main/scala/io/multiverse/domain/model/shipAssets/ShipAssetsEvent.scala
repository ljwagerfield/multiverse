package io.multiverse.domain.model.shipAssets

import io.multiverse.domain.model.instance.InstanceId

/**
 * Event relating to the binary assets for a type of ship.
 */
trait ShipAssetsEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Ship assets relative to the event.
   */
  val shipAssetsId: ShipAssetsId
}
