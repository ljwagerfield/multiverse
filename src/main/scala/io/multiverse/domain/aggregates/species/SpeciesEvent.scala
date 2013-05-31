package io.multiverse.domain.aggregates.species

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to a species within the universe.
 */
trait SpeciesEvent extends InstanceEvent {
  val speciesId: SpeciesId
}
