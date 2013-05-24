package io.multiverse.domain.model.user

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.shared.{Email, Hash}

/**
 * New user registered to play the game.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param userId Unique new user ID.
 * @param email Unique point of contact.
 * @param password Account password specified by user.
 * @param emailVerificationCode Code sent to provided email. Must be submitted back to application to verify the account.
 */
case class UserRegistered(instanceId: InstanceId, timeStamp: Long, userId: UserId, email: Email, password: Hash, emailVerificationCode: Hash) extends UserEvent

/**
 * User verified their email by submitting a code which matched the one persisted in their registration event.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param userId User being verified.
 */
case class UserEmailVerified(instanceId: InstanceId, timeStamp: Long, userId: UserId) extends UserEvent
