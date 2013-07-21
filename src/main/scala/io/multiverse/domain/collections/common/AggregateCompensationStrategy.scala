package io.multiverse.domain.collections.common

import io.multiverse.domain.aggregates.common.{AggregateRoot, UnconditionalCommand}

/**
 * Aggregates multiple strategies for compensating data inconsistencies cause by multi-master replication.
 * @tparam A Aggregate type.
 * @tparam E Event type.
 * @tparam Conflict Conflict type to resolve.
 * @tparam Command Command type to compensate conflicts with.
 * @param strategies Underlying strategies in order of precedence.
 */
class AggregateCompensationStrategy[A <: AggregateRoot[A, E], E, Conflict, Command <: UnconditionalCommand[A, E]]
  (strategies: List[CompensationStrategy[A, E, Conflict, Command]])
  extends CompensationStrategy[A, E, Conflict, Command] {
  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @return Unresolved conflicts. Commands used to compensate resolved conflicts.
   */
  def resolve(conflicts: List[Conflict]): (List[Conflict], List[Command]) =
    ((conflicts, List[Command]())/:strategies)((c, s) => s.resolve(c._1))
}