package io.multiverse.domain.model.speciesFlagBackgroundVector

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.values.Hash

/**
 * Species flag background vector defined.
 * @param speciesFlagBackgroundVectorId Unique ID for the flag background vector.
 * @param hash Reference to the BLOB containing vector data.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SpeciesFlagBackgroundVectorDefined(speciesFlagBackgroundVectorId: SpeciesFlagBackgroundVectorId,
                                              hash: Hash,
                                              instanceId: InstanceId,
                                              timestamp: Long) extends SpeciesFlagBackgroundVectorEvent
