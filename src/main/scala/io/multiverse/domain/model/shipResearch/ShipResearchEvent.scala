package io.multiverse.domain.model.shipResearch

import io.multiverse.domain.model.instance.{InstanceId, InstanceEvent}
import io.multiverse.domain.model.species.SpeciesId

/**
 * Event relating to a species research on ships.
 */
trait ShipResearchEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Species relative to the event.
   */
  val speciesId: SpeciesId
}
