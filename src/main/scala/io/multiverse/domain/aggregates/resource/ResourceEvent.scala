package io.multiverse.domain.aggregates.resource

import io.multiverse.domain.aggregates.instance.InstanceId

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
