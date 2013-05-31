package io.multiverse.domain.aggregates.system

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to a type resource of resource within the universe.
 */
trait SystemEvent extends InstanceEvent {
  val systemId: SystemId
}
