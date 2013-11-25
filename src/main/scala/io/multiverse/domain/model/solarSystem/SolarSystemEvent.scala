package io.multiverse.domain.model.solarSystem

import io.multiverse.domain.model.instance.{InstanceId, InstanceEvent}

/**
 * Event relating to a specific solar system.
 */
trait SolarSystemEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Solar system relative to the event.
   */
  val starId: StarId
}
