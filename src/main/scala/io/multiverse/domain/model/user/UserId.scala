package io.multiverse.domain.model.user

import java.util.UUID

/**
 * Uniquely identifies a user entity.
 * @param value Unique user ID.
 */
case class UserId(value: UUID)
