package io.multiverse.domain.collections.user

import io.multiverse.domain.aggregates.user.UserId

/**
 * Duplicate email conflict.
 * @param userId User with the conflicting email.
 */
case class DuplicateEmail(userId: UserId) extends UserConflict
