package io.multiverse.domain.model.user.commands

import io.multiverse.domain.model.common.commands.{TailCommand, CommandPrerequisite}
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.{UserEmailVerified, User, UserCommand, UserId}

/**
 * Verifies the user's email using the provided code.
 * @param userId User receiving the command.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class VerifyUserEmail(userId: UserId, code: Hash, instanceId: InstanceId, timestamp: Long)
  extends UserCommand with TailCommand[User] {

  /**
   * Predicates that must pass for the command to be successfully evaluated.
   */
  val prerequisites = List(VerifyUserEmail.VerificationCodeMatch(this))

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: User#Root): List[User#Event] =
    if (aggregateRoot.isVerified)
      Nil // Idempotent command.
    else
      List(UserEmailVerified(userId, instanceId, timestamp))
}

/**
 * Command prerequisites for [[io.multiverse.domain.model.user.commands.VerifyUserEmail]].
 */
object VerifyUserEmail {

  /**
   * Requires the provided email verification code to match the code generated at registration.
   * @param command Command to validate.
   */
  case class VerificationCodeMatch(command: VerifyUserEmail) extends CommandPrerequisite[User] {

    /**
     * Tests the validity of the prerequisite given the aggregate state.
     * @param aggregate Aggregate the command is being tested against.
     * @return True if the rule passes, otherwise false.
     */
    def isValid(aggregate: User): Boolean =
      aggregate.isVerified || aggregate.emailVerificationCode.forall(_ == command.code)
  }
}
