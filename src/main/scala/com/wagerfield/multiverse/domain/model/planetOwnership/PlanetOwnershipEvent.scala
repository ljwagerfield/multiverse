package com.wagerfield.multiverse.domain.model.planetOwnership

import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId
import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to planet ownership.
 */
trait PlanetOwnershipEvent extends InstanceEvent {
	val planetId: PlanetId
}
