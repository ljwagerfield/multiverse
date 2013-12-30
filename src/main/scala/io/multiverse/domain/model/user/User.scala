package io.multiverse.domain.model.user

import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.common.{Aggregate, AggregateRootBase, ExplicitAggregateFactory, Entity}

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
  extends AggregateRootBase[User, UserEvent] with Aggregate[User] with Entity[UserId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[User] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[User#Event, Aggregate[User]] = {
    case event: UserEmailVerified => copy(changes = changes :+ event, isVerified = true, emailVerificationCode = None)
    case event: UserDeduplicated => copy(changes = changes :+ event, canonicalUserId = Some(event.canonicalUserId))
  }
}

/**
 * User factory.
 */
object User extends ExplicitAggregateFactory[User] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: UserRegistered): Aggregate[User] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[User#Event, Aggregate[User]] = {
    case event: UserRegistered => User(Nil :+ event, event.userId, isVerified = false, Some(event.emailVerificationCode), None)
  }
}
