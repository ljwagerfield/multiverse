package io.multiverse.domain.aggregates.instance

/**
 * Event relating to an instance.
 */
trait InstanceEvent {
  val instanceId: InstanceId
}
