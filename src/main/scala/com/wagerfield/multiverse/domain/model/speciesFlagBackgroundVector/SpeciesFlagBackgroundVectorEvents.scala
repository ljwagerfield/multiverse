package com.wagerfield.multiverse.domain.model.speciesFlagBackgroundVector

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash

/**
 * Species flag background vector defined.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param speciesFlagBackgroundVectorId Unique ID for the flag background vector.
 * @param hash Reference to the BLOB containing vector data.
 */
case class SpeciesFlagBackgroundVectorDefined(instanceId:InstanceId,
                                              timeStamp:Long,
                                              speciesFlagBackgroundVectorId:SpeciesFlagBackgroundVectorId,
                                              hash:Hash) extends SpeciesFlagBackgroundVectorEvent
