package io.multiverse.domain.collections.user

import io.multiverse.domain.aggregates.user.{DeduplicateUser, UserEvent, User, UserCommand}
import io.multiverse.domain.aggregates.common.UnconditionalCommand

/**
 * Resolves duplicate email inconsistencies.
 */
class DuplicateEmailCompensationStrategy extends UserCompensationStrategy {
  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @return Unresolved conflicts and compensations.
   */
  def resolve(conflicts: List[UserConflict]): (List[UserConflict], List[UserCommand with UnconditionalCommand[User, UserEvent]]) = {
    //val (resolved, unresolved) = conflicts.partition(_.isInstanceOf[DuplicateEmail])
    //(unresolved, resolved.map(_.asInstanceOf[DuplicateEmail]).map(c => DeduplicateUser(c.userId, c.)))
    sys.error("Not implemented")
  }
}
