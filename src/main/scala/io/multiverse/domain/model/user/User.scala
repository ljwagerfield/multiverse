package io.multiverse.domain.model.user

import io.multiverse.domain.model.common.{CommandTarget, ExplicitAggregateFactory, AggregateRoot, Entity}
import io.multiverse.domain.model.common.values.Hash

/**
 * Registered user.
 * @param changes Events pending commitment.
 * @param id Unique user ID.
 */
case class User private(changes: List[UserEvent],
                        id: UserId,
                        isVerified: Boolean,
                        emailVerificationCode: Option[Hash],
                        canonicalUserId: Option[UserId])
  extends Entity[UserId] with AggregateRoot[User, UserEvent] with CommandTarget[UserCommand] {

  /**
   * Clears the backlog of uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: User = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return User with event appended and new state applied.
   */
  def apply(event: UserEvent): User = event match {
    case event: UserEmailVerified => copy(changes = changes :+ event, isVerified = true, emailVerificationCode = None)
    case event: UserDeduplicated => copy(changes = changes :+ event, canonicalUserId = Some(event.canonicalUserId))
    case event: UserEvent => unhandledEvent(event)
  }
}

/**
 * User factory.
 */
object User extends ExplicitAggregateFactory[User, UserEvent] {

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return User with event appended and new state applied.
   */
  def apply(event: UserEvent): User = {
    event match {
      case event: UserRegistered => User(Nil :+ event, event.userId, isVerified = false, Some(event.emailVerificationCode), None)
      case event: UserEvent => unhandledEvent(event)
    }
  }
}
