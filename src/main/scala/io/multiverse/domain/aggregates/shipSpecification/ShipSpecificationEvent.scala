package io.multiverse.domain.aggregates.shipSpecification

import io.multiverse.domain.aggregates.instance.InstanceEvent
import io.multiverse.domain.aggregates.species.SpeciesId

/**
 * Event relating to a ship specification.
 */
trait ShipSpecificationEvent extends InstanceEvent {
  val shipSpecificationId: ShipSpecificationId
}
