package com.wagerfield.multiverse.domain.model.shipSpecification

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent
import com.wagerfield.multiverse.domain.model.species.SpeciesId

/**
 * Event relating to a ship specification.
 */
trait ShipSpecificationEvent extends InstanceEvent {
  val shipSpecificationId: ShipSpecificationId
}
