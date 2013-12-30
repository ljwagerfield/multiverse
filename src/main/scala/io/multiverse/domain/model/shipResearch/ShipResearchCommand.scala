package io.multiverse.domain.model.shipResearch

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.species.SpeciesId

/**
 * Command relating to a resource.
 */
trait ShipResearchCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Species relative to the command.
   */
  val speciesId: SpeciesId
}
