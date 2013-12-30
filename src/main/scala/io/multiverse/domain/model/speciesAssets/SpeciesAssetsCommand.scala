package io.multiverse.domain.model.speciesAssets

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to the binary assets for a species.
 */
trait SpeciesAssetsCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Species assets relative to the command.
   */
  val speciesAssetsId: SpeciesAssetsId
}
