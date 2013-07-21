package io.multiverse.domain.aggregates.shipResearch

import io.multiverse.domain.aggregates.instance.{InstanceId, InstanceEvent}
import io.multiverse.domain.aggregates.species.SpeciesId

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
