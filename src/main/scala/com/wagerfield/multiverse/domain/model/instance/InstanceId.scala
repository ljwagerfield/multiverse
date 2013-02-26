package com.wagerfield.multiverse.domain.model.instance

import java.util.UUID

/**
 * Uniquely identifies an instance.
 * @param value The unique identifier.
 */
case class InstanceId(value: UUID)