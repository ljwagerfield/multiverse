package com.wagerfield.multiverse.domain.model.shipAssets

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash

/**
 * Ship assets defined: ID and size form composite key; size ranges 1 to 5 (scout, destroyer, transporter, carrier, leviathan).
 */
case class ShipAssetsDefined(instanceId:InstanceId, shipAssetsId:ShipAssetsId, size:Int, hash:Hash) extends ShipAssetsEvent