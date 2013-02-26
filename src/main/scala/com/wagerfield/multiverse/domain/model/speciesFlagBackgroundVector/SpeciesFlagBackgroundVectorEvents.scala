package com.wagerfield.multiverse.domain.model.speciesFlagBackgroundVector

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash

/**
 * Species flag background vector defined.
 * @param instanceId Instance the event occurred in.
 * @param speciesFlagBackgroundVectorId Unique ID for the flag background vector.
 * @param hash Reference to the BLOB containing vector data.
 */
case class SpeciesFlagBackgroundVectorDefined(instanceId:InstanceId, speciesFlagBackgroundVectorId:SpeciesFlagBackgroundVectorId, hash:Hash) extends SpeciesFlagBackgroundVectorEvent