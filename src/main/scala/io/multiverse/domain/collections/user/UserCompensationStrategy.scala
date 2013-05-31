package io.multiverse.domain.collections.user

import io.multiverse.domain.collections.common.{CompensationStrategy, AggregateCompensationStrategy}
import io.multiverse.domain.aggregates.user.UserCommand

/**
 * Resolves user data inconsistencies resulting from multi-master replication.
 */
trait UserCompensationStrategy extends CompensationStrategy[UserConflict, UserCommand]

/**
 * User conflict resolution strategy factory.
 */
object UserCompensationStrategy {
  /**
   * Singleton instance of the user conflict resolution strategy.
   * @return Conflict resolution strategy.
   */
  def instance: UserCompensationStrategy = {
    new AggregateCompensationStrategy[UserConflict, UserCommand](Nil) with UserCompensationStrategy
  }
}
