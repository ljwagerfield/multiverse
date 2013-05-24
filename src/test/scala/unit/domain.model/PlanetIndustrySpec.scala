package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.planetIndustry.{ShipBuildCommissioned, PlanetIndustry}
import io.multiverse.domain.model.ship.ShipId
import io.multiverse.domain.model.shipSpecification.ShipSpecificationId
import io.multiverse.domain.model.solarSystem.PlanetId
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
