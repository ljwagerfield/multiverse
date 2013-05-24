package io.multiverse.domain.model.user

import io.multiverse.domain.shared.{Hash, Email, Entity, ExplicitAggregateFactory, AggregateRoot}
import io.multiverse.domain.model.instance.InstanceId

/**
 * Registered user.
 * @param changes Events pending commitment.
 * @param id Unique user ID.
 */
case class User private(changes: List[UserEvent], id: UserId, isVerified: Boolean, emailVerificationCode: Option[Hash])
  extends Entity[UserId] with AggregateRoot[User, UserEvent] {
  /**
   * Clears the backlog of uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: User = copy(changes = Nil)

  /**
   * Verifies the user's email using the provided code.
   * @param code Verification code sent to the user's email account.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Verified user.
   */
  def verifyEmail(code: Hash, instanceId: InstanceId, timeStamp: Long) = {
    if (isVerified) {
      this // Idempotent command.
    }
    else {
      require(emailVerificationCode.forall(_ == code), "Email verification code must match the code generated at registration.")
      applyEvent(UserEmailVerified(instanceId, timeStamp, id))
    }
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return User with event appended and new state applied.
   */
  def applyEvent(event: UserEvent): User = {
    event match {
      case event: UserEmailVerified => copy(changes = changes :+ event, isVerified = true, emailVerificationCode = None)
      case event: UserEvent => unhandled(event)
    }
  }
}

/**
 * User factory.
 */
object User extends ExplicitAggregateFactory[User, UserEvent] {
  /**
   * Creates a new user.
   * @param userId Unique ID for the new user.
   * @param email Unique point of contact.
   * @param password Account password specified by user.
   * @param emailVerificationCode Code sent to provided email. Must be submitted back to application to verify the account.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New user.
   */
  def register(userId: UserId, email: Email, password: Hash, emailVerificationCode: Hash, instanceId: InstanceId, timeStamp: Long): User =
    applyEvent(UserRegistered(instanceId, timeStamp, userId, email, password, emailVerificationCode))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return User with event appended and new state applied.
   */
  def applyEvent(event: UserEvent): User = {
    event match {
      case event: UserRegistered => User(Nil :+ event, event.userId, isVerified = false, Some(event.emailVerificationCode))
      case event: UserEvent => unhandled(event)
    }
  }
}
