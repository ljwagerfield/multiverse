package io.multiverse.domain.aggregates.shipResearch

import io.multiverse.domain.aggregates.instance.InstanceEvent
import io.multiverse.domain.aggregates.species.SpeciesId

/**
 * Event relating to a species research on ships.
 */
trait ShipResearchEvent extends InstanceEvent {
  val speciesId: SpeciesId
}
