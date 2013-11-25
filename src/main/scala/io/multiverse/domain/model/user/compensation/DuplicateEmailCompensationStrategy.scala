package io.multiverse.domain.model.user.compensation

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.commands.DeduplicateUser
import io.multiverse.domain.model.user.{DuplicateEmail, UserConflict, UserCompensationStrategy, UserEvent, User, UserCommand}
import io.multiverse.domain.model.common.commands.UnconditionalCommand

/**
 * Resolves duplicate email inconsistencies.
 */
class DuplicateEmailCompensationStrategy extends UserCompensationStrategy {
  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @param instanceId Instance invoking the command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Unresolved conflicts and compensations.
   */
  def resolve(conflicts: List[UserConflict], instanceId: InstanceId, timestamp: Long): (List[UserConflict], List[UserCommand with UnconditionalCommand[User, UserEvent]]) = {
    val (resolved, unresolved) = conflicts.partition(_.isInstanceOf[DuplicateEmail])
    (unresolved, resolved.map(_.asInstanceOf[DuplicateEmail]).map(c => DeduplicateUser(c.userId, c.canonicalUserId, instanceId, timestamp)))
  }
}
