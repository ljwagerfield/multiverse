package io.multiverse.domain.model.planetOwnership

import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to planet ownership.
 */
trait PlanetOwnershipEvent extends InstanceEvent {
  val planetId: PlanetId
}
