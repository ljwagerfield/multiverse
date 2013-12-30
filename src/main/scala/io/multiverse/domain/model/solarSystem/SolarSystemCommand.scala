package io.multiverse.domain.model.solarSystem

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a solar system.
 */
trait SolarSystemCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Solar system relative to the command.
   */
  val starId: StarId
}
