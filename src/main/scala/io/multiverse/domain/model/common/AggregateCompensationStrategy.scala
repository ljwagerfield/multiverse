package io.multiverse.domain.model.common

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.instance.InstanceId

/**
 * Aggregates multiple strategies for compensating data inconsistencies cause by multi-master replication.
 * @tparam A Aggregate meta type.
 * @tparam Conflict Conflict type to resolve.
 * @tparam Command Command type to compensate conflicts with.
 * @param strategies Underlying strategies in order of precedence.
 */
class AggregateCompensationStrategy[A <: AggregateMeta, Conflict, Command <: UnconditionalTailCommand[A]]
  (strategies: List[CompensationStrategy[A, Conflict, Command]])
  extends CompensationStrategy[A, Conflict, Command] {

  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @return Unresolved conflicts. Commands used to compensate resolved conflicts.
   */
  def resolve(conflicts: List[Conflict], instanceId: InstanceId, timestamp: Long): (List[Conflict], List[Command]) = {
    val unresolved = (conflicts, List[Command]())
    (unresolved /: strategies)((c, s) => s.resolve(c._1, instanceId, timestamp))
  }
}
