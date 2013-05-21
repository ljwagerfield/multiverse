package io.multiverse.domain.model.species

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a species within the universe.
 */
trait SpeciesEvent extends InstanceEvent {
  val speciesId: SpeciesId
}
