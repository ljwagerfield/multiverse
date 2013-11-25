package io.multiverse.domain.model.instance.commands

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.instance.{UserSignedOut, InstanceEvent, Instance, InstanceCommand, InstanceId}

/**
 * Signs out the currently signed-in user.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SignOut(instanceId: InstanceId, timestamp: Long)
  extends InstanceCommand with UnconditionalTailCommand[Instance, InstanceEvent, SignOut] {

  /**
   * Evaluates the effect of this command against the given aggregate.
   * @param aggregate Aggregate to evaluate command against.
   * @return Evaluation of the events caused by invoking this command against the given aggregate.
   */
  protected def evaluationForValidInput(aggregate: Instance): List[InstanceEvent] =
    if (aggregate.signedInUser.isEmpty)
      Nil // Idempotent command.
    else
      List(UserSignedOut(instanceId, timestamp))
}
