package io.multiverse.domain.aggregates.instance

/**
 * Command relating to an instance.
 */
trait InstanceCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId
}
