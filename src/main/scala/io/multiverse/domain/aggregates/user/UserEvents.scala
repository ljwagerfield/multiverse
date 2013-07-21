package io.multiverse.domain.aggregates.user

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.{Email, Hash}

/**
 * New user registered to play the game.
 * @param userId Unique new user ID.
 * @param email Unique point of contact.
 * @param password Account password specified by user.
 * @param emailVerificationCode Code sent to provided email. Must be submitted back to application to verify the account.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class UserRegistered(userId: UserId, email: Email, password: Hash, emailVerificationCode: Hash, instanceId: InstanceId, timestamp: Long) extends UserEvent

/**
 * User verified their email by submitting a code which matched the one persisted in their registration event.
 * @param userId User being verified.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class UserEmailVerified(userId: UserId, instanceId: InstanceId, timestamp: Long) extends UserEvent

/**
 * User was recognised as being a duplicate of another existing user. All future events will reference the canonical user.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param userId User being deduplicated.
 * @param canonicalUserId Canonical user the deduplicate is being removed for
 */
case class UserDeduplicated(userId: UserId, canonicalUserId: UserId, instanceId: InstanceId, timestamp: Long) extends UserEvent
