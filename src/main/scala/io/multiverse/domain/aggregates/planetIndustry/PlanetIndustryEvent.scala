package io.multiverse.domain.aggregates.planetIndustry

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.solarSystem.PlanetId

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
