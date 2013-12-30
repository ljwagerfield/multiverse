package io.multiverse.domain.model.instance.commands

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.instance.{UserSignedOut, InstanceEvent, Instance, InstanceCommand, InstanceId}

/**
 * Signs out the currently signed-in user.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SignOut(instanceId: InstanceId, timestamp: Long)
  extends InstanceCommand with UnconditionalTailCommand[Instance] {

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: Instance): List[InstanceEvent] =
    if (aggregateRoot.signedInUser.isEmpty)
      Nil // Idempotent command.
    else
      List(UserSignedOut(instanceId, timestamp))
}
