package io.multiverse.domain.model.user

/**
 * Duplicate email conflict.
 * @param userId User with the conflicting email.
 * @param canonicalUserId First user known to exist with this email.
 */
case class DuplicateEmail(userId: UserId, canonicalUserId: UserId) extends UserConflict
