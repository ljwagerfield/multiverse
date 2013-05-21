package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.planetIndustry.ShipBuildCommissioned
import io.multiverse.domain.model.ship.{ShipDestroyed, ShipDecommissioned, ShipHaltOrdered, ShipCoordinatesOrdered, StarOffset, PlanetOrbitOrdered, PlanetColonizationOrdered, PlanetAttackOrdered, ShipAttackOrdered, ShipBuilt, SolarSystemEntryOrdered, ShipId, Ship}
import io.multiverse.domain.model.shipSpecification.ShipSpecificationId
import io.multiverse.domain.model.solarSystem.{StarId, PlanetId}
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Ship specification.
 */
class ShipSpec extends Specification {
	"un-built ship" should {
		"have its build finalized" in new ShipScope {
			val shipBuildEvent = ShipBuildCommissioned(instanceId, timestamp, PlanetId(UUID.randomUUID), ShipSpecificationId(UUID.randomUUID), shipId)
			Ship.finalizeBuild(shipBuildEvent, instanceId, timestamp)
				.changes must contain(ShipBuilt(instanceId, timestamp, shipId, shipBuildEvent))
		}
	}

	"ship" should {
		"attack non-friendly ship" in new BuiltShipScope {
			val nonFriendlyShipId = ShipId(UUID.randomUUID)
			ship.attack(nonFriendlyShipId, instanceId, timestamp)
				.changes must contain(ShipAttackOrdered(instanceId, timestamp, shipId, nonFriendlyShipId))
		}

		"not attack itself" in new BuiltShipScope {
			ship.attack(shipId, instanceId, timestamp) must throwA[Exception]
		}

		"attack non-friendly planet" in new BuiltShipScope {
			val nonFriendlyPlanetId = PlanetId(UUID.randomUUID)
			ship.attack(nonFriendlyPlanetId, instanceId, timestamp)
				.changes must contain(PlanetAttackOrdered(instanceId, timestamp, shipId, nonFriendlyPlanetId))
		}

		"colonize vacant planet" in new BuiltShipScope {
			val vacantPlanetId = PlanetId(UUID.randomUUID)
			ship.colonize(vacantPlanetId, instanceId, timestamp)
				.changes must contain(PlanetColonizationOrdered(instanceId, timestamp, shipId, vacantPlanetId))
		}

		"orbit non-hostile planet" in new BuiltShipScope {
			val nonHostilePlanetId = PlanetId(UUID.randomUUID)
			ship.orbit(nonHostilePlanetId, instanceId, timestamp)
				.changes must contain(PlanetOrbitOrdered(instanceId, timestamp, shipId, nonHostilePlanetId))
		}

		"move to star offset" in new BuiltShipScope {
			val starId = StarId(UUID.randomUUID)
			val offset = StarOffset(0, 0)
			ship.moveTo(starId, offset, instanceId, timestamp)
				.changes must contain(ShipCoordinatesOrdered(instanceId, timestamp, shipId, starId, offset))
		}

    "move to star" in new BuiltShipScope {
      val starId = StarId(UUID.randomUUID)
      ship.moveTo(starId, instanceId, timestamp)
        .changes must contain(SolarSystemEntryOrdered(instanceId, timestamp, shipId, starId))
    }

    "halt" in new BuiltShipScope {
      ship.halt(instanceId, timestamp)
        .changes must contain(ShipHaltOrdered(instanceId, timestamp, shipId))
    }

    "decommission" in new BuiltShipScope {
      val decommissionedEvent = ShipDecommissioned(instanceId, timestamp, shipId)
      ship.decommission(instanceId, timestamp)
        .changes must contain(decommissionedEvent, ShipDestroyed(instanceId, timestamp, shipId, decommissionedEvent))
    }
	}

	/**
	 * Predefined test values for ship.
	 */
	trait ShipScope extends InstanceScope {
		val shipId = ShipId(UUID.randomUUID)
	}

	/**
	 * Predefined test values for built ships.
	 */
	trait BuiltShipScope extends ShipScope {
		val shipBuildEvent = ShipBuildCommissioned(instanceId, timestamp, PlanetId(UUID.randomUUID), ShipSpecificationId(UUID.randomUUID), shipId)
		val ship = Ship.finalizeBuild(shipBuildEvent, instanceId, timestamp)
	}
}
