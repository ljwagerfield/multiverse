package io.multiverse.domain.model.system

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a system.
 */
trait SystemCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * System relative to the command.
   */
  val systemId: SystemId
}
