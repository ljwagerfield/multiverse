package io.multiverse.domain.aggregates.shipAssets

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to the binary assets for a type of ship.
 */
trait ShipAssetsEvent extends InstanceEvent {
  val shipAssetsId: ShipAssetsId
}
