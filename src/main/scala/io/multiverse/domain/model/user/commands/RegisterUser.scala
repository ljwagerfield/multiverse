package io.multiverse.domain.model.user.commands

import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.values.{Hash, Email}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.{UserRegistered, User, UserCommand, UserId}
import io.multiverse.domain.model.common.Aggregate

/**
 * Creates a new user.
 * @param userId Unique ID for the new user.
 * @param email Unique point of contact.
 * @param password Account password specified by user.
 * @param emailVerificationCode Code sent to provided email. Must be submitted back to application to verify the account.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class RegisterUser(userId: UserId,
                        email: Email,
                        password: Hash,
                        emailVerificationCode: Hash,
                        instanceId: InstanceId,
                        timestamp: Long) extends UserCommand with HeadCommand[User] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  lazy val evaluation: Aggregate[User] =
    User(UserRegistered(userId, email, password, emailVerificationCode, instanceId, timestamp))
}
