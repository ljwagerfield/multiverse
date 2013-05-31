package io.multiverse.domain.aggregates.planetIndustry

import io.multiverse.domain.aggregates.solarSystem.PlanetId
import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to planet industry.
 */
trait PlanetIndustryEvent extends InstanceEvent {
  val planetId: PlanetId
}
