package io.multiverse.domain.aggregates.instance

import io.multiverse.domain.aggregates.common.{ExplicitAggregateFactory, AggregateRoot, Entity}
import io.multiverse.domain.aggregates.user.UserId

/**
 * Running instance of the application.
 * @param changes Events pending commitment.
 * @param id Unique instance ID.
 * @param signedInUser User currently signed-into the instance.
 */
case class Instance private(changes: List[InstanceEvent],
                            id: InstanceId,
                            signedInUser: Option[UserId])
  extends Entity[InstanceId] with AggregateRoot[Instance, InstanceEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Instance = copy(changes = Nil)

  /**
   * Signs a user into this instance.
   * @param user User to be signed-in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate with signed-in user.
   */
  def signIn(user:UserId, timeStamp:Long): Instance = {
    require(signedInUser.forall(_ == user), "Instances only support a single signed-in user.")
    if (signedInUser.isDefined) {
      this // Idempotent command.
    }
    else {
      applyEvent(UserSignedIn(id, timeStamp, user))
    }
  }

  /**
   * Signs the current user out of this instance.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate without a signed-in user.
   */
  def signOut(timeStamp:Long): Instance = {
    if (signedInUser.isEmpty) {
      this // Idempotent command.
    }
    else {
      applyEvent((UserSignedOut(id, timeStamp)))
    }
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: InstanceEvent): Instance = {
    event match {
      case event:UserSignedIn => copy(changes = changes :+ event, signedInUser = Some(event.userId))
      case event:UserSignedOut => copy(changes = changes :+ event, signedInUser = None)
      case event: InstanceEvent => unhandled(event)
    }
  }
}

/**
 * Instance factory.
 */
object Instance extends ExplicitAggregateFactory[Instance, InstanceEvent] {
  /**
   * Creates a new instance.
   * @param instanceId Unique ID for the new instance.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @param version Application version number.
   * @return Aggregate representing new instance.
   */
  def create(instanceId:InstanceId, timeStamp:Long, version:Version):Instance =
    applyEvent(InstanceCreated(instanceId, timeStamp, version))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: InstanceEvent): Instance = {
    event match {
      case event: InstanceCreated => Instance(Nil :+ event, event.instanceId, None)
      case event: InstanceEvent => unhandled(event)
    }
  }
}
