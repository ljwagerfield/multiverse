package com.wagerfield.multiverse.domain.model.ship

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Event relating to a specific ship.
 */
trait ShipEvent extends InstanceEvent {
	val shipId: ShipId
}
