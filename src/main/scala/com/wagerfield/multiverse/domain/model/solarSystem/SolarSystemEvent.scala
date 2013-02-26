package com.wagerfield.multiverse.domain.model.solarSystem

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a specific solar system.
 */
trait SolarSystemEvent extends InstanceEvent {
	val starId: StarId
}
