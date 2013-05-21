package io.multiverse.domain.model.speciesFlagEmblemVector

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to the binary vector asset for the emblem on a species flag.
 */
trait SpeciesFlagEmblemVectorEvent extends InstanceEvent {
  val speciesFlagEmblemVectorId: SpeciesFlagEmblemVectorId
}
