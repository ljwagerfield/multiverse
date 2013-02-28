package com.wagerfield.multiverse.domain.model.system

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a type resource of resource within the universe.
 */
trait SystemEvent extends InstanceEvent {
  val systemId: SystemId
}
