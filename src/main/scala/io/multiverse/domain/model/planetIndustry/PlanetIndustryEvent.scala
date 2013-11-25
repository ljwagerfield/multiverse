package io.multiverse.domain.model.planetIndustry

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Event relating to planet industry.
 */
trait PlanetIndustryEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Planet relative to the event.
   */
  val planetId: PlanetId
}
