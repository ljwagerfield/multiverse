package io.multiverse.domain.model.user.compensation

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.commands.DeduplicateUser
import io.multiverse.domain.model.user.{DuplicateEmail, UserConflict, UserCompensationStrategy, User, UserCommand}

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
  def resolve(conflicts: List[UserConflict], instanceId: InstanceId, timestamp: Long):
    (List[UserConflict], List[UserCommand with UnconditionalTailCommand[User]]) =
    conflicts.map {
      case resolved: DuplicateEmail => (Nil, List(DeduplicateUser(resolved.userId, resolved.canonicalUserId, instanceId, timestamp)))
      case unresolved: UserConflict => (List(unresolved), Nil)
    }.reduce((a, b) => (a._1 ::: b._1, a._2 ::: b._2))
}
