package io.multiverse.domain.model.speciesFlagEmblemVector

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to the binary assets for a flag emblem vector.
 */
trait SpeciesFlagEmblemVectorCommand {

  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * Vector asset relative to the command.
   */
  val speciesFlagEmblemVectorId: SpeciesFlagEmblemVectorId
}
