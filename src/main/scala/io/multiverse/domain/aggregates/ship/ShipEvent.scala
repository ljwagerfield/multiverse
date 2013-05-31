package io.multiverse.domain.aggregates.ship

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to a ship.
 */
trait ShipEvent extends InstanceEvent {
  val shipId: ShipId
}
