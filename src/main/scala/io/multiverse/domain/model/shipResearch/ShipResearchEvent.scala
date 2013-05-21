package io.multiverse.domain.model.shipResearch

import io.multiverse.domain.model.instance.InstanceEvent
import io.multiverse.domain.model.species.SpeciesId

/**
 * Event relating to a species research on ships.
 */
trait ShipResearchEvent extends InstanceEvent {
  val speciesId: SpeciesId
}
