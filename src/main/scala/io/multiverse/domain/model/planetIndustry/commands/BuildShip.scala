package io.multiverse.domain.model.planetIndustry.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.planetIndustry.{ShipBuildCommissioned, PlanetIndustryEvent, PlanetIndustry, PlanetIndustryCommand}
import io.multiverse.domain.model.ship.ShipId
import io.multiverse.domain.model.shipSpecification.ShipSpecificationId
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Commissions a new ship build.
 * @param planetId Planet the ship is being built at.
 * @param shipSpecificationId Specification the new ship will be based on.
 * @param shipId Unique ID for the new ship.
 * @param instanceId Instance invoking this command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class BuildShip(planetId: PlanetId, shipSpecificationId:ShipSpecificationId, shipId:ShipId, instanceId:InstanceId, timestamp:Long)
  extends PlanetIndustryCommand with StaticTailCommand[PlanetIndustry] {

  /**
   * The effect of this command.
   */
  val events: List[PlanetIndustryEvent] =
    List(ShipBuildCommissioned(planetId, shipSpecificationId, shipId, instanceId, timestamp))
}
