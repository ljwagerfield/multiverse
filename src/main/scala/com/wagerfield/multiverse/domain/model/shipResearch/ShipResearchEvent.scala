package com.wagerfield.multiverse.domain.model.shipResearch

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent
import com.wagerfield.multiverse.domain.model.species.SpeciesId

/**
 * Event relating to a species research on ships.
 */
trait ShipResearchEvent extends InstanceEvent {
  val speciesId: SpeciesId
}
