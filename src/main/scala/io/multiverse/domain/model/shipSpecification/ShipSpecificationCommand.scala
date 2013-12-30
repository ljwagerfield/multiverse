package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a ship specification.
 */
trait ShipSpecificationCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Ship specification relative to the command.
   */
  val shipSpecificationId: ShipSpecificationId
}
