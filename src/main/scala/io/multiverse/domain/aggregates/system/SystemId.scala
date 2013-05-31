package io.multiverse.domain.aggregates.system

import java.util.UUID

/**
 * Uniquely identifies the system resource. System is a singleton under normal use.
 * @param value Unique system ID.
 */
case class SystemId(value: UUID)
