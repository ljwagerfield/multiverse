package io.multiverse.domain.model.instance.commands

import io.multiverse.domain.model.common.commands.{TailCommand, CommandPrerequisite}
import io.multiverse.domain.model.instance.{UserSignedIn, InstanceCommand, InstanceEvent, Instance, InstanceId}
import io.multiverse.domain.model.user.UserId

/**
 * Signs a user into this instance.
 * @param userId User being signed in.
 * @param instanceId Instance receiving the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SignIn(userId: UserId, instanceId: InstanceId, timestamp: Long)
  extends InstanceCommand with TailCommand[Instance] {

  /**
   * Predicates that must pass for the command to be successfully evaluated.
   */
  val prerequisites = List(SignIn.SignedInUserSingularity(this))

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: Instance): List[InstanceEvent] =
    if (aggregateRoot.signedInUser.isDefined)
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
   * @param command Command to validate.
   */
  case class SignedInUserSingularity(command: SignIn) extends CommandPrerequisite[Instance] {

    /**
     * Tests the validity of the prerequisite given the aggregate state.
     * @param aggregate Aggregate the command is being tested against.
     * @return True if the rule passes, otherwise false.
     */
    def isValid(aggregate: Instance): Boolean =
      aggregate.signedInUser.forall(_ == command.userId)
  }
}
