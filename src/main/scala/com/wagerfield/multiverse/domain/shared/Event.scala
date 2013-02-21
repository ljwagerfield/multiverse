package com.wagerfield.multiverse.domain.shared

import java.util.UUID

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Object that is not defined by its attributes, but rather by a thread of continuity and its identity.
 */
trait Event {
	val token: EventToken
	val aggregateId: UUID
	val aggregateType: String
}
