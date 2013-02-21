package com.wagerfield.multiverse.domain.model.resource

import com.wagerfield.multiverse.domain.model.instance.InstanceEvent

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Event relating to a specific ship.
 */
trait ResourceEvent extends InstanceEvent {
	val resourceId: ResourceId
}
