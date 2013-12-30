package io.multiverse.domain.model.resource

import io.multiverse.domain.model.common.{Entity, Aggregate, AggregateRootBase, ExplicitAggregateFactory}
import io.multiverse.domain.model.common.values.{ShortAlphabeticName, IntegralPercentage}
import io.multiverse.domain.model.instance.{InstanceCreated, InstanceId}
import io.multiverse.domain.model.planetOwnership.PlanetOwnershipEvent

/**
 * Dependable resource within the universe.
 * @param changes Events pending commitment.
 */
case class Resource private(changes: List[ResourceEvent], id: ResourceId)
  extends AggregateRootBase[Resource, ResourceEvent] with Aggregate[Resource] with Entity[ResourceId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[Resource] = copy(changes = Nil)
}

/**
 * Resource factory.
 */
object Resource extends ExplicitAggregateFactory[Resource] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: ResourceDefined): Aggregate[Resource] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[Resource#Event, Aggregate[Resource]] = {
    case event: ResourceDefined => Resource(Nil :+ event, event.resourceId)
  }
}
