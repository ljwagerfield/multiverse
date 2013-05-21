package io.multiverse.domain.model.ship

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a ship.
 */
trait ShipEvent extends InstanceEvent {
  val shipId: ShipId
}
