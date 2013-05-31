package io.multiverse.domain.aggregates.instance

/**
 * Command relating to an instance.
 */
trait InstanceCommand {
  val instanceId: InstanceId
}
