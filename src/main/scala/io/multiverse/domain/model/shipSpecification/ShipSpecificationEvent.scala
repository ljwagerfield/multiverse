package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.instance.{InstanceId, InstanceEvent}
import io.multiverse.domain.model.species.SpeciesId

/**
 * Event relating to a ship specification.
 */
trait ShipSpecificationEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Ship specification relative to the event.
   */
  val shipSpecificationId: ShipSpecificationId
}
