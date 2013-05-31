package io.multiverse.domain.aggregates.planetOwnership

import io.multiverse.domain.aggregates.solarSystem.PlanetId
import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to planet ownership.
 */
trait PlanetOwnershipEvent extends InstanceEvent {
  val planetId: PlanetId
}
