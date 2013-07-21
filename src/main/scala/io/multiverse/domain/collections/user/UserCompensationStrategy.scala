package io.multiverse.domain.collections.user

import io.multiverse.domain.collections.common.{CompensationStrategy, AggregateCompensationStrategy}
import io.multiverse.domain.aggregates.user.{User, UserEvent, UserCommand}
import io.multiverse.domain.aggregates.common.UnconditionalCommand

/**
 * Resolves user data inconsistencies resulting from multi-master replication.
 */
trait UserCompensationStrategy extends CompensationStrategy[User, UserEvent, UserConflict, UserCommand with UnconditionalCommand[User, UserEvent]]

/**
 * User conflict resolution strategy factory.
 */
object UserCompensationStrategy {

  /**
   * Singleton instance of the user conflict resolution strategy.
   * @return Conflict resolution strategy.
   */
  def instance: UserCompensationStrategy =
    new AggregateCompensationStrategy[User, UserEvent, UserConflict, UserCommand with UnconditionalCommand[User, UserEvent]](Nil) with UserCompensationStrategy
}
