package io.multiverse.domain.aggregates.instance

/**
 * Event relating to an instance.
 */
trait InstanceEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId
}
