package com.wagerfield.multiverse.domain.model.ship

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a ship.
 */
trait ShipEvent extends InstanceEvent {
	val shipId: ShipId
}
