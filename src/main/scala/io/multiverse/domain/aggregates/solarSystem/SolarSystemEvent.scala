package io.multiverse.domain.aggregates.solarSystem

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to a specific solar system.
 */
trait SolarSystemEvent extends InstanceEvent {
  val starId: StarId
}
