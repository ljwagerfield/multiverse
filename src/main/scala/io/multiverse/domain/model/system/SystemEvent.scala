package io.multiverse.domain.model.system

import io.multiverse.domain.model.instance.InstanceId

/**
 * Event relating to a type resource of resource within the universe.
 */
trait SystemEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * System relative to the event.
   */
  val systemId: SystemId
}
