package io.multiverse.domain.model.system

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a type resource of resource within the universe.
 */
trait SystemEvent extends InstanceEvent {
  val systemId: SystemId
}
