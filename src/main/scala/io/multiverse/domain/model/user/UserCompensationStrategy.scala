package io.multiverse.domain.model.user

import io.multiverse.domain.model.common.commands.UnconditionalCommand
import io.multiverse.domain.model.common.{CompensationStrategy, AggregateCompensationStrategy}
import io.multiverse.domain.model.user.compensation.DuplicateEmailCompensationStrategy

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
    new AggregateCompensationStrategy[User, UserEvent, UserConflict, UserCommand
      with UnconditionalCommand[User, UserEvent]](List(new DuplicateEmailCompensationStrategy()))
      with UserCompensationStrategy
}
