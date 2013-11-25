package io.multiverse.domain.model.instance

/**
 * Command relating to an instance.
 */
trait InstanceCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId
}
