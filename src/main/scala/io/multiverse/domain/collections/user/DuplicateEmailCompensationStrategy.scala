package io.multiverse.domain.collections.user

import io.multiverse.domain.aggregates.user.UserCommand

/**
 * Resolves duplicate email inconsistencies.
 */
class DuplicateEmailCompensationStrategy extends UserCompensationStrategy {
  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @return Unresolved conflicts and compensations.
   */
  def resolve(conflicts: List[UserConflict]): (List[UserConflict], List[UserCommand]) = {
    sys.error("Not implemented.")
  }
}
