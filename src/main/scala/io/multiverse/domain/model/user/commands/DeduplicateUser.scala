package io.multiverse.domain.model.user.commands

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.{UserDeduplicated, User, UserCommand, UserId}

/**
 * Marks this entity as a duplicate of the provided user. All afferent references must be updated to the canonical user.
 * @param userId User being deduplicated.
 * @param canonicalUserId User this entity is a duplicate of.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class DeduplicateUser(userId: UserId, canonicalUserId: UserId, instanceId: InstanceId, timestamp: Long)
  extends UserCommand with UnconditionalTailCommand[User] {

  require(canonicalUserId != userId, "Canonical user must be a different user.")

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: User#Root): List[User#Event] =
    if (aggregateRoot.canonicalUserId.isDefined)
      Nil // Idempotent command.
    else
      List(UserDeduplicated(userId, canonicalUserId, instanceId, timestamp))
}

