package com.wagerfield.multiverse.domain.shared

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Object that is not defined by its attributes, but rather by a thread of continuity and its identity.
 */
trait Entity[Id] {
	val id: Id
}
