package io.multiverse.domain.aggregates.speciesFlagEmblemVector

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to the binary vector asset for the emblem on a species flag.
 */
trait SpeciesFlagEmblemVectorEvent extends InstanceEvent {
  val speciesFlagEmblemVectorId: SpeciesFlagEmblemVectorId
}
