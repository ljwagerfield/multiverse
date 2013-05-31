package io.multiverse.domain.aggregates.speciesFlagBackgroundVector

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to the binary vector asset for the background of a species flag.
 */
trait SpeciesFlagBackgroundVectorEvent extends InstanceEvent {
  val speciesFlagBackgroundVectorId: SpeciesFlagBackgroundVectorId
}
