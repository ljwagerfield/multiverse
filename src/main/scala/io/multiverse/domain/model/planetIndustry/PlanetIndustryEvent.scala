package io.multiverse.domain.model.planetIndustry

import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to planet industry.
 */
trait PlanetIndustryEvent extends InstanceEvent {
  val planetId: PlanetId
}
