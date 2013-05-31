package io.multiverse.domain.aggregates.instance

import java.util.UUID

/**
 * Uniquely identifies an instance.
 * @param value The unique identifier.
 */
case class InstanceId(value: UUID)
