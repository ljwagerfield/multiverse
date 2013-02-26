package com.wagerfield.multiverse.domain.shared

/**
 * Object that is not defined by its attributes, but rather by a thread of continuity and its identity.
 */
trait Entity[Id] {
	val id: Id
}
