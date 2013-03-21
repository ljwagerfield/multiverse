package com.wagerfield.multiverse.domain.model.resource

import com.wagerfield.multiverse.domain.shared.{ValidatedEntityAggregateFactory, AggregateRoot}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Dependable resource within the universe.
 * @param uncommittedEvents Events pending commitment.
 */
case class Resource private(uncommittedEvents: List[ResourceEvent])
  extends AggregateRoot[Resource, ResourceEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Resource = copy(uncommittedEvents = Nil)

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
object Resource extends ValidatedEntityAggregateFactory[Resource, ResourceEvent] {
  /**
   * Defines a new resource within the universe.
   * @param resourceId Unique ID for new resource.
   * @param name Unique resource name within the universe
   * @param description Description of the resource's unique properties.
   * @param abundance Resource abundance, ranging from 0-100.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New resource.
   */
  def define(resourceId:ResourceId, name:String, description:String, abundance:Int, instanceId:InstanceId, timeStamp:Long):Resource =
    applyEvent(ResourceDefined(instanceId, timeStamp, resourceId, name, description, abundance))

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
