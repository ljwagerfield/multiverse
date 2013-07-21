package io.multiverse.domain.aggregates.speciesAssets

import io.multiverse.domain.aggregates.instance.InstanceId

/**
 * Event relating to the binary assets for a type of ship.
 */
trait SpeciesAssetsEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Species assets relative to the event.
   */
  val speciesAssetsId: SpeciesAssetsId
}
