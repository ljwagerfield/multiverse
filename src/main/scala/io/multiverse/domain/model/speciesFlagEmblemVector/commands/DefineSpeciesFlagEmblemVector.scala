package io.multiverse.domain.model.speciesFlagEmblemVector.commands

import io.multiverse.domain.model.common.Aggregate
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.speciesFlagEmblemVector.{SpeciesFlagEmblemVectorCommand, SpeciesFlagEmblemVectorDefined, SpeciesFlagEmblemVector, SpeciesFlagEmblemVectorId}


/**
 * Defines new binary assets for use in the emblems for species flags.
 * @param speciesFlagEmblemVectorId Unique ID for new species assets.
 * @param hash References the binary assets.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class DefineSpeciesFlagEmblemVector(speciesFlagEmblemVectorId:SpeciesFlagEmblemVectorId,
                                             hash: Hash,
                                             instanceId: InstanceId,
                                             timestamp: Long)
  extends SpeciesFlagEmblemVectorCommand with HeadCommand[SpeciesFlagEmblemVector] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  val evaluation: Aggregate[SpeciesFlagEmblemVector] =
    SpeciesFlagEmblemVector(SpeciesFlagEmblemVectorDefined(speciesFlagEmblemVectorId, hash, instanceId, timestamp))
}
