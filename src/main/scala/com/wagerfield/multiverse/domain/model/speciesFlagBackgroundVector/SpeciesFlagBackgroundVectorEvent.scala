package com.wagerfield.multiverse.domain.model.speciesFlagBackgroundVector

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to the binary vector asset for the background of a species flag.
 */
trait SpeciesFlagBackgroundVectorEvent extends InstanceEvent {
	val speciesFlagBackgroundVectorId: SpeciesFlagBackgroundVectorId
}
