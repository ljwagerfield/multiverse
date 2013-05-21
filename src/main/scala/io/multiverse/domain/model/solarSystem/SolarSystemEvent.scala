package io.multiverse.domain.model.solarSystem

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a specific solar system.
 */
trait SolarSystemEvent extends InstanceEvent {
  val starId: StarId
}
