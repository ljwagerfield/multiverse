package io.multiverse.domain.model.speciesFlagEmblemVector

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.values.Hash

/**
 * Species flag emblem vector defined.
 * @param speciesFlagEmblemVectorId Unique ID for the flag background vector.
 * @param hash Reference to the BLOB containing vector data.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SpeciesFlagEmblemVectorDefined(speciesFlagEmblemVectorId: SpeciesFlagEmblemVectorId,
                                          hash: Hash,
                                          instanceId: InstanceId,
                                          timestamp: Long) extends SpeciesFlagEmblemVectorEvent
