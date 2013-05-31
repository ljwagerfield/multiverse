package io.multiverse.domain.aggregates.resource

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.{IntegralPercentage, ShortAlphabeticName, ExplicitAggregateFactory, AggregateRoot}

/**
 * Dependable resource within the universe.
 * @param changes Events pending commitment.
 */
case class Resource private(changes: List[ResourceEvent])
  extends AggregateRoot[Resource, ResourceEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Resource = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Resource with event appended and new state applied.
   */
  def applyEvent(event: ResourceEvent): Resource = unhandled(event)
}

/**
 * Resource factory.
 */
object Resource extends ExplicitAggregateFactory[Resource, ResourceEvent] {
  /**
   * Defines a new resource within the universe.
   * @param resourceId Unique ID for new resource.
   * @param name Unique resource name within the universe.
   * @param description Description of the resource's unique attributes.
   * @param abundance Resource abundance, ranging from 0-100.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New resource.
   */
  def define(resourceId:ResourceId,
             name:ShortAlphabeticName,
             description:String,
             abundance:IntegralPercentage,
             instanceId:InstanceId,
             timeStamp:Long):Resource = {
    require(description.length >= 100 && description.length <= 150, "Description must be between 100 and 150 characters, inclusive.")
    applyEvent(ResourceDefined(instanceId, timeStamp, resourceId, name, description, abundance))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Resource with event appended and new state applied.
   */
  def applyEvent(event: ResourceEvent):Resource = {
    event match {
      case event: ResourceDefined => Resource(Nil :+ event)
      case event: ResourceEvent => unhandled(event)
    }
  }
}
