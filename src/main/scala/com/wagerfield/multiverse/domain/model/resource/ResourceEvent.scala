package com.wagerfield.multiverse.domain.model.resource

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a type resource of resource within the universe.
 */
trait ResourceEvent extends InstanceEvent {
  val resourceId: ResourceId
}
