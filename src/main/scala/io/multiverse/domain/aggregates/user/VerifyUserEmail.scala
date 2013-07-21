package io.multiverse.domain.aggregates.user

import io.multiverse.domain.aggregates.common.{ConditionalTailCommand, Hash, CommandPrerequisite}
import io.multiverse.domain.aggregates.instance.InstanceId

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
 * Command prerequisites for [[io.multiverse.domain.aggregates.user.VerifyUserEmail]].
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
