package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.instance.InstanceEvent
import io.multiverse.domain.model.species.SpeciesId

/**
 * Event relating to a ship specification.
 */
trait ShipSpecificationEvent extends InstanceEvent {
  val shipSpecificationId: ShipSpecificationId
}
