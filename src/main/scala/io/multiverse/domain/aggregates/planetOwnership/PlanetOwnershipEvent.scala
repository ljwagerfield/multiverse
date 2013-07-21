package io.multiverse.domain.aggregates.planetOwnership

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.solarSystem.PlanetId

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
