package com.wagerfield.multiverse.domain.model.ship

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Event relating to a specific ship.
 */
trait ShipEvent {
	val shipId: ShipId
}
