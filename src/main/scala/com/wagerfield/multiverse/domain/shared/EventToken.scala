package com.wagerfield.multiverse.domain.shared

import java.util.UUID

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Object that is not defined by its attributes, but rather by a thread of continuity and its identity.
 */
trait EventToken {
	val value: UUID
}
