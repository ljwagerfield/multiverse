package io.multiverse.domain.aggregates.resource

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to a type resource of resource within the universe.
 */
trait ResourceEvent extends InstanceEvent {
  val resourceId: ResourceId
}
