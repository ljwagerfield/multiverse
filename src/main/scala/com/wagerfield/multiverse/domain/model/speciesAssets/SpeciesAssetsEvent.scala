package com.wagerfield.multiverse.domain.model.speciesAssets

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to the binary assets for a type of ship.
 */
trait SpeciesAssetsEvent extends InstanceEvent {
	val speciesAssetsId: SpeciesAssetsId
}
