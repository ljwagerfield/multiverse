package io.multiverse.domain.model.ship

import io.multiverse.domain.model.instance.InstanceId

/**
 * Event relating to a ship.
 */
trait ShipEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Ship relative to the event.
   */
  val shipId: ShipId
}
