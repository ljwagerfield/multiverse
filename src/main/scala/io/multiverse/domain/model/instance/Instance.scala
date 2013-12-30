package io.multiverse.domain.model.instance

import io.multiverse.domain.model.common.{Entity, Aggregate, AggregateRootBase, ExplicitAggregateFactory}
import io.multiverse.domain.model.user.UserId

/**
 * Running instance of the application
 * @param changes Events pending commitment.
 * @param id Unique instance ID.
 * @param signedInUser User currently signed-into the instance.
 */
case class Instance private(changes: List[InstanceEvent], id: InstanceId, signedInUser: Option[UserId])
  extends AggregateRootBase[Instance, InstanceEvent] with Aggregate[Instance] with Entity[InstanceId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[Instance] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[Instance#Event, Aggregate[Instance]] = {
    case event: UserSignedIn => copy(changes = changes :+ event, signedInUser = Some(event.userId))
    case event: UserSignedOut => copy(changes = changes :+ event, signedInUser = None)
  }
}

/**
 * Instance factory.
 */
object Instance extends ExplicitAggregateFactory[Instance] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: InstanceCreated): Aggregate[Instance] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[Instance#Event, Aggregate[Instance]] = {
    case event: InstanceCreated => Instance(Nil :+ event, event.instanceId, None)
  }
}
