package io.multiverse.domain.aggregates.speciesFlagBackgroundVector

import io.multiverse.domain.aggregates.instance.InstanceId

/**
 * Event relating to the binary vector asset for the background of a species flag.
 */
trait SpeciesFlagBackgroundVectorEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Vector relative to the event.
   */
  val speciesFlagBackgroundVectorId: SpeciesFlagBackgroundVectorId
}
