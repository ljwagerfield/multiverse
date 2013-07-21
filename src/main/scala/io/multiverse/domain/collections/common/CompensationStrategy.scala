package io.multiverse.domain.collections.common

import io.multiverse.domain.aggregates.common.{AggregateRoot, UnconditionalCommand}

/**
 * Resolves data inconsistencies resulting from multi-master replication.
 * @tparam A Aggregate type.
 * @tparam E Event type.
 * @tparam Conflict Conflict type to resolve.
 * @tparam Command Command type used to compensate conflicts.
 */
trait CompensationStrategy[A <: AggregateRoot[A, E], E, Conflict, Command <: UnconditionalCommand[A, E]] {
  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @return Unresolved conflicts and compensations.
   */
  def resolve(conflicts: List[Conflict]): (List[Conflict], List[Command])
}
