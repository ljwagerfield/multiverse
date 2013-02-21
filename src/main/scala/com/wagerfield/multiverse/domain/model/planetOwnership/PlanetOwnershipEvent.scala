package com.wagerfield.multiverse.domain.model.planetOwnership

import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId
import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Event relating to a specific instance.
 */
trait PlanetOwnershipEvent extends InstanceEvent {
	val planetId: PlanetId
}
