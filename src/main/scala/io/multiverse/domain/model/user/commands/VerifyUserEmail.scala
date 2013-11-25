package io.multiverse.domain.model.user.commands

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.{commands, UserEmailVerified, UserEvent, User, UserCommand, UserId}
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.common.commands.{CommandPrerequisite, ConditionalTailCommand}

/**
 * Verifies the user's email using the provided code.
 * @param userId User receiving the command.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class VerifyUserEmail(userId: UserId, code: Hash, instanceId: InstanceId, timestamp: Long)
  extends UserCommand with ConditionalTailCommand[User, UserEvent, VerifyUserEmail] {

  /**
   * Command prerequisites.
   */
  val prerequisites = List(VerifyUserEmail.VerificationCodeMatch(this))

  /**
   * Evaluates the effect of this command against the given aggregate, assuming no failing prerequisites.
   * @param aggregate Aggregate to evaluate command against.
   * @return Evaluation of the events caused by invoking this command against the given aggregate.
   */
  protected def evaluationForValidInput(aggregate: User): List[UserEvent] =
    if (aggregate.isVerified)
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
   */
  case class VerificationCodeMatch(command: VerifyUserEmail) extends CommandPrerequisite[User, VerifyUserEmail] {

    /**
     * Tests the validity of the prerequisite given the aggregate state.
     * @param aggregate Aggregate the command is being tested against.
     * @return True if the rule passes, otherwise false.
     */
    def isValid(aggregate: User): Boolean =
      aggregate.isVerified || aggregate.emailVerificationCode.forall(_ == command.code)
  }
}
