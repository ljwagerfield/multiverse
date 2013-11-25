package io.multiverse.domain.model.speciesFlagEmblemVector

import io.multiverse.domain.model.instance.InstanceId

/**
 * Event relating to the binary vector asset for the emblem on a species flag.
 */
trait SpeciesFlagEmblemVectorEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Vector relative to the event.
   */
  val speciesFlagEmblemVectorId: SpeciesFlagEmblemVectorId
}
