package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.planetIndustry.{ShipBuildCommissioned, PlanetIndustry}
import io.multiverse.domain.aggregates.ship.ShipId
import io.multiverse.domain.aggregates.shipSpecification.ShipSpecificationId
import io.multiverse.domain.aggregates.solarSystem.PlanetId
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Planet industry specification.
 */
class PlanetIndustrySpec extends Specification {
  "planet industry" should {
    "be initialized" in new PlanetIndustryScope {
      PlanetIndustry.init(planetId)
        .changes must beEmpty
    }

    "build ships" in new PlanetIndustryScope {
      var shipSpecificationId = ShipSpecificationId(UUID.randomUUID)
      val shipId = ShipId(UUID.randomUUID)
      PlanetIndustry
        .init(planetId)
        .buildShip(shipSpecificationId, shipId, instanceId, timestamp)
        .changes must beEqualTo(List(
          ShipBuildCommissioned(instanceId, timestamp, planetId, shipSpecificationId, shipId)))
    }
  }

  /**
   * Predefined test values for planet industry.
   */
  trait PlanetIndustryScope extends InstanceScope {
    val planetId = PlanetId(UUID.randomUUID)
  }
}
