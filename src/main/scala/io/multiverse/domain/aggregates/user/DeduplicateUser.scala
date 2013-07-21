package io.multiverse.domain.aggregates.user

import io.multiverse.domain.aggregates.common.{UnconditionalTailCommand, CommandPrerequisite}
import io.multiverse.domain.aggregates.instance.InstanceId

/**
 * Marks this entity as a duplicate of the provided user. All afferent references must be updated to the canonical user.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param userId User being deduplicated.
 * @param canonicalUserId User this entity is a duplicate of.
 */
case class DeduplicateUser(userId: UserId, canonicalUserId: UserId, instanceId: InstanceId, timestamp: Long)
  extends UserCommand with UnconditionalTailCommand[User, UserEvent, DeduplicateUser] {
  require(canonicalUserId != userId, "Canonical user must be a different user.")

  /**
   * Evaluates the effect of this command against the given aggregate, assuming no failing prerequisites.
   * @param aggregate Aggregate to evaluate command against.
   * @return Evaluation of the events caused by invoking this command against the given aggregate.
   */
  protected def evaluationForValidInput(aggregate: User): List[UserEvent] =
    if (aggregate.canonicalUserId.isDefined)
      Nil // Idempotent command.
    else
      List(UserDeduplicated(userId, canonicalUserId, instanceId, timestamp))
}

