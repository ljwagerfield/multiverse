package io.multiverse.domain.model.common

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.instance.InstanceId

/**
 * Resolves data inconsistencies resulting from multi-master replication.
 * @tparam A Aggregate meta type.
 * @tparam Conflict Conflict type to resolve.
 * @tparam Command Command type used to compensate conflicts.
 */
trait CompensationStrategy[A <: AggregateMeta, Conflict, Command <: UnconditionalTailCommand[A]] {

  /**
   * Attempts to resolve the provided conflicts.
   * @param conflicts Conflicts to resolve.
   * @param instanceId Instance invoking the command.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Unresolved conflicts and compensations.
   */
  def resolve(conflicts: List[Conflict], instanceId: InstanceId, timestamp: Long): (List[Conflict], List[Command])
}
