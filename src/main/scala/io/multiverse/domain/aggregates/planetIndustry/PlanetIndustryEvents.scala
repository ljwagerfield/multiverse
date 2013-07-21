package io.multiverse.domain.aggregates.planetIndustry

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.ship.ShipId
import io.multiverse.domain.aggregates.shipSpecification.ShipSpecificationId
import io.multiverse.domain.aggregates.solarSystem.PlanetId

/**
 * Ship build commissioned: ships always built in serial.
 * @param planetId Planet where the build was commissioned.
 * @param shipSpecificationId Specification the ship will be built against.
 * @param shipId Unique ID for the new ship.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipBuildCommissioned(planetId:PlanetId,
                                 shipSpecificationId:ShipSpecificationId,
                                 shipId:ShipId,
                                 instanceId:InstanceId,
                                 timestamp:Long) extends PlanetIndustryEvent