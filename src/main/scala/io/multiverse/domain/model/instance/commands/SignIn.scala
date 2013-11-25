package io.multiverse.domain.model.instance.commands

import io.multiverse.domain.model.common.commands.{CommandPrerequisite, ConditionalTailCommand}
import io.multiverse.domain.model.instance.{UserSignedIn, InstanceCommand, InstanceEvent, Instance, InstanceId}
import io.multiverse.domain.model.user.UserId

/**
 * Signs a user into this instance.
 * @param userId User being signed in.
 * @param instanceId Instance receiving the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SignIn(userId: UserId, instanceId: InstanceId, timestamp: Long)
  extends InstanceCommand with ConditionalTailCommand[Instance, InstanceEvent, SignIn] {

  /**
   * Command prerequisites.
   */
  val prerequisites = List(SignIn.SignedInUserSingularity(this))

  /**
   * Evaluates the effect of this command against the given aggregate, assuming no failing prerequisites.
   * @param aggregate Aggregate to evaluate command against.
   * @return Evaluation of the events caused by invoking this command against the given aggregate.
   */
  protected def evaluationForValidInput(aggregate: Instance): List[InstanceEvent] =
    if (aggregate.signedInUser.isDefined)
      Nil
    else
      List(UserSignedIn(instanceId, userId, timestamp))
}

/**
 * Command prerequisites for [[io.multiverse.domain.model.instance.commands.SignIn]].
 */
object SignIn {

  /**
   * Instances only support a single signed-in user.
   */
  case class SignedInUserSingularity(command: SignIn) extends CommandPrerequisite[Instance, SignIn] {

    /**
     * Tests the validity of the prerequisite given the aggregate state.
     * @param aggregate Aggregate the command is being tested against.
     * @return True if the rule passes, otherwise false.
     */
    def isValid(aggregate: Instance): Boolean =
      aggregate.signedInUser.forall(_ == command.userId)
  }

}
