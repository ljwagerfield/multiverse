package io.multiverse.domain.model.planetOwnership

import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a planet's ownership.
 */
trait PlanetOwnershipCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Planet relative to the command.
   */
  val planetId: PlanetId
}
