package com.wagerfield.multiverse.domain.model.shipAssets

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash

/**
 * Ship assets defined: ID and size form composite key.
 * @param instanceId Instance the event occurred in.
 * @param shipAssetsId Unique ID for the new ship assets.
 * @param size Ship size ranges 1 to 5 (scout, destroyer, transporter, carrier, leviathan).
 * @param hash References the binary assets.
 */
case class ShipAssetsDefined(instanceId:InstanceId, shipAssetsId:ShipAssetsId, size:Int, hash:Hash) extends ShipAssetsEvent