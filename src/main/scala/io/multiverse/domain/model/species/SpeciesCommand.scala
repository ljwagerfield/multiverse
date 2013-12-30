package io.multiverse.domain.model.species

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a species.
 */
trait SpeciesCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Species relative to the command.
   */
  val speciesId: SpeciesId
}
