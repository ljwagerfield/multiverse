package com.wagerfield.multiverse.domain.model.species

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a species within the universe.
 */
trait SpeciesEvent extends InstanceEvent {
  val speciesId: SpeciesId
}
