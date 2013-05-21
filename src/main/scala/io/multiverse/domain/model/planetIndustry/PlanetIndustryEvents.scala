package io.multiverse.domain.model.planetIndustry

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.ship.{ShipEvent, ShipId}
import io.multiverse.domain.model.shipSpecification.ShipSpecificationId

/**
 * Ship build commissioned: ships always built in serial.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param planetId Planet where the build was commissioned.
 * @param shipSpecificationId Specification the ship will be built against.
 * @param shipId Unique ID for the new ship.
 */
case class ShipBuildCommissioned(instanceId:InstanceId,
                                 timeStamp:Long,
                                 planetId:PlanetId,
                                 shipSpecificationId:ShipSpecificationId,
                                 shipId:ShipId) extends PlanetIndustryEvent
