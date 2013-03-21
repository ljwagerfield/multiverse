package com.wagerfield.multiverse.domain.model.planetIndustry

import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId
import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to planet industry.
 */
trait PlanetIndustryEvent extends InstanceEvent {
  val planetId: PlanetId
}
