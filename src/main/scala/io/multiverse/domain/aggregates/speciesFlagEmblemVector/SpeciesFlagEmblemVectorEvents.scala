package io.multiverse.domain.aggregates.speciesFlagEmblemVector

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.Hash

/**
 * Species flag emblem vector defined.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param speciesFlagEmblemVectorId Unique ID for the flag background vector.
 * @param hash Reference to the BLOB containing vector data.
 */
case class SpeciesFlagEmblemVectorDefined(instanceId:InstanceId,
                                          timeStamp:Long,
                                          speciesFlagEmblemVectorId:SpeciesFlagEmblemVectorId,
                                          hash:Hash) extends SpeciesFlagEmblemVectorEvent
