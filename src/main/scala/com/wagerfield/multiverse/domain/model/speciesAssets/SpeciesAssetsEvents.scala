package com.wagerfield.multiverse.domain.model.speciesAssets

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash

/**
 * Species assets defined.
 */
case class SpeciesAssetsDefined(instanceId:InstanceId, speciesAssetsId:SpeciesAssetsId, hash:Hash) extends SpeciesAssetsEvent