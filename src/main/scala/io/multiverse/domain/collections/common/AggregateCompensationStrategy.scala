package io.multiverse.domain.collections.common

/**
 * Aggregates multiple strategies to resolve data inconsistencies for multi-master replication.
 * @tparam Conflict Type of conflict to resolve.
 * @tparam Command Type of command to resolve conflicts with.
 * @param strategies Underlying strategies in order of precedence.
 */
class AggregateCompensationStrategy[Conflict, Command](strategies: List[CompensationStrategy[Conflict, Command]]) extends CompensationStrategy[Conflict, Command] {
  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @return Unresolved conflicts.
   */
  def resolve(conflicts: List[Conflict]): (List[Conflict], List[Command]) = {
    sys.error("Not implemented.")
    // (conflicts/:strategies)((c, s) => s.resolve(c))
  }
}
