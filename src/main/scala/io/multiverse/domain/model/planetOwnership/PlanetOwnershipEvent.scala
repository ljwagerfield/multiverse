package io.multiverse.domain.model.planetOwnership

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Event relating to planet ownership.
 */
trait PlanetOwnershipEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Planet relative to the event.
   */
  val planetId: PlanetId
}
