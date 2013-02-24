package com.wagerfield.multiverse.domain.model.shipAssets

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to the binary assets for a type of ship.
 */
trait ShipAssetsEvent extends InstanceEvent {
	val shipAssetsId: ShipAssetsId
}
