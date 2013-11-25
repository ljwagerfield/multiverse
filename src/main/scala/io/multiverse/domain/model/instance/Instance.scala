package io.multiverse.domain.model.instance

import io.multiverse.domain.model.common.{ExplicitAggregateFactory, AggregateRoot, Entity}
import io.multiverse.domain.model.user.UserId

/**
 * Running instance of the application.
 * @param changes Events pending commitment.
 * @param id Unique instance ID.
 * @param signedInUser User currently signed-into the instance.
 */
case class Instance private(changes: List[InstanceEvent],
                            id: InstanceId,
                            signedInUser: Option[UserId])
  extends Entity[InstanceId] with AggregateRoot[Instance, InstanceEvent] {

  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Instance = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def apply(event: InstanceEvent): Instance = {
    event match {
      case event: UserSignedIn => copy(changes = changes :+ event, signedInUser = Some(event.userId))
      case event: UserSignedOut => copy(changes = changes :+ event, signedInUser = None)
      case event: InstanceEvent => unhandledEvent(event)
    }
  }
}

/**
 * Instance factory.
 */
object Instance extends ExplicitAggregateFactory[Instance, InstanceEvent] {

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def apply(event: InstanceEvent): Instance = {
    event match {
      case event: InstanceCreated => Instance(Nil :+ event, event.instanceId, None)
      case event: InstanceEvent => unhandledEvent(event)
    }
  }
}
