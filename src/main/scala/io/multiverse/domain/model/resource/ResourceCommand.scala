package io.multiverse.domain.model.resource

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a resource.
 */
trait ResourceCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Resource relative to the command.
   */
  val resourceId: ResourceId
}
