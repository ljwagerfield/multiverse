package io.multiverse.domain.model.species

import io.multiverse.domain.model.instance.InstanceId

/**
 * Event relating to a species within the universe.
 */
trait SpeciesEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Species relative to the event.
   */
  val speciesId: SpeciesId
}
