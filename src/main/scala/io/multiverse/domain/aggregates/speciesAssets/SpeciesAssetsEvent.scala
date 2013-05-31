package io.multiverse.domain.aggregates.speciesAssets

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to the binary assets for a type of ship.
 */
trait SpeciesAssetsEvent extends InstanceEvent {
  val speciesAssetsId: SpeciesAssetsId
}
