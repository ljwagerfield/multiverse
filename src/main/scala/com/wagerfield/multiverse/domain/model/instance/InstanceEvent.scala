package com.wagerfield.multiverse.domain.model.instance

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Event relating to a specific instance.
 */
trait InstanceEvent {
	val instanceId: InstanceId
}
