package io.multiverse.domain.collections.common

/**
 * Resolves data inconsistencies resulting from multi-master replication.
 * @tparam Conflict Type of conflict to resolve.
 * @tparam Command Type of command to resolve conflicts with.
 */
trait CompensationStrategy[Conflict, Command] {
  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @return Unresolved conflicts and compensations.
   */
  def resolve(conflicts: List[Conflict]): (List[Conflict], List[Command])
}
