package io.multiverse.domain.model.speciesFlagBackgroundVector

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to the binary assets for a flag background vector.
 */
trait SpeciesFlagBackgroundVectorCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Vector asset relative to the command.
   */
  val speciesFlagBackgroundVectorId: SpeciesFlagBackgroundVectorId
}
