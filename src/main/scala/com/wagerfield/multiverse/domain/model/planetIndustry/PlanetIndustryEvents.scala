package com.wagerfield.multiverse.domain.model.planetIndustry

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId
import com.wagerfield.multiverse.domain.model.ship.{ShipEvent, ShipId}
import com.wagerfield.multiverse.domain.model.shipSpecification.ShipSpecificationId

/**
 * Ship build commissioned: ships always built in serial.
 */
case class ShipBuildCommissioned(instanceId:InstanceId, planetId:PlanetId, shipSpecificationId:ShipSpecificationId, shipId:ShipId) extends ShipEvent