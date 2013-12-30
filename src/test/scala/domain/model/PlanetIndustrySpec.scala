package domain.model

import baseSpecifications.CommandCombinators.{aggregateToTestChain, staticCommandToTestChain}
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.planetIndustry.commands.BuildShip
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
      (PlanetIndustry(planetId)
        yields Nil)
    }

    "build ships" in new PlanetIndustryScope {
      var shipSpecificationId = ShipSpecificationId(UUID.randomUUID)
      val shipId = ShipId(UUID.randomUUID)

      (BuildShip(planetId, shipSpecificationId, shipId, instanceId, timestamp)
        yields ShipBuildCommissioned(planetId, shipSpecificationId, shipId, instanceId, timestamp))
    }
  }

  /**
   * Predefined test values for planet industry.
   */
  trait PlanetIndustryScope extends InstanceScope {
    val planetId = PlanetId(UUID.randomUUID)
  }
}
