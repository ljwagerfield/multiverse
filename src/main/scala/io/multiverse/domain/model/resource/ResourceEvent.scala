package io.multiverse.domain.model.resource

import io.multiverse.domain.model.instance.InstanceId

/**
 * Event relating to a type resource of resource within the universe.
 */
trait ResourceEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * Resource relative to the event.
   */
  val resourceId: ResourceId
}
