package io.multiverse.domain.model.speciesFlagBackgroundVector.commands

import io.multiverse.domain.model.speciesFlagBackgroundVector.{SpeciesFlagBackgroundVectorDefined, SpeciesFlagBackgroundVector, SpeciesFlagBackgroundVectorCommand, SpeciesFlagBackgroundVectorId}
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.Aggregate


/**
 * Defines new binary assets for use in the backgrounds for species flags.
 * @param speciesFlagBackgroundVectorId Unique ID for new species assets.
 * @param hash References the binary assets.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class DefineSpeciesFlagBackgroundVector(speciesFlagBackgroundVectorId:SpeciesFlagBackgroundVectorId,
                                             hash: Hash,
                                             instanceId: InstanceId,
                                             timestamp: Long)
  extends SpeciesFlagBackgroundVectorCommand with HeadCommand[SpeciesFlagBackgroundVector] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  val evaluation: Aggregate[SpeciesFlagBackgroundVector] =
    SpeciesFlagBackgroundVector(SpeciesFlagBackgroundVectorDefined(speciesFlagBackgroundVectorId, hash, instanceId, timestamp))
}
