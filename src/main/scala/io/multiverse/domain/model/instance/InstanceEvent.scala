package io.multiverse.domain.model.instance

/**
 * Event relating to an instance.
 */
trait InstanceEvent {
  val instanceId: InstanceId
}
