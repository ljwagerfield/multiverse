package com.wagerfield.multiverse.domain.model.speciesFlagEmblemVector

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash

/**
 * Species flag emblem vector defined.
 * @param instanceId Instance the event occurred in.
 * @param speciesFlagEmblemVectorId Unique ID for the flag background vector.
 * @param hash Reference to the BLOB containing vector data.
 */
case class SpeciesFlagEmblemVectorDefined(instanceId:InstanceId, speciesFlagEmblemVectorId:SpeciesFlagEmblemVectorId, hash:Hash) extends SpeciesFlagEmblemVectorEvent