package domain.model

import _root_.baseSpecifications.InstanceScope
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
			val shipBuildEvent = ShipBuildCommissioned(PlanetId(UUID.randomUUID), ShipSpecificationId(UUID.randomUUID), shipId, instanceId, timestamp)
			Ship
        .finalizeBuild(shipBuildEvent, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipBuilt(shipId, shipBuildEvent, instanceId, timestamp)))
		}
	}

	"ship" should {
		"attack non-friendly ship" in new BuiltShipScope {
			val nonFriendlyShipId = ShipId(UUID.randomUUID)
			ship
        .attack(nonFriendlyShipId, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipAttackOrdered(shipId, nonFriendlyShipId, instanceId, timestamp)))
		}

		"not attack itself" in new BuiltShipScope {
			ship.attack(shipId, instanceId, timestamp) must throwA[Exception]
		}

		"attack non-friendly planet" in new BuiltShipScope {
			val nonFriendlyPlanetId = PlanetId(UUID.randomUUID)
			ship
        .attack(nonFriendlyPlanetId, instanceId, timestamp)
				.changes must beEqualTo(List(
          PlanetAttackOrdered(shipId, nonFriendlyPlanetId, instanceId, timestamp)))
		}

		"colonize vacant planet" in new BuiltShipScope {
			val vacantPlanetId = PlanetId(UUID.randomUUID)
			ship
        .colonize(vacantPlanetId, instanceId, timestamp)
				.changes must beEqualTo(List(
          PlanetColonizationOrdered(shipId, vacantPlanetId, instanceId, timestamp)))
		}

		"orbit non-hostile planet" in new BuiltShipScope {
			val nonHostilePlanetId = PlanetId(UUID.randomUUID)
			ship
        .orbit(nonHostilePlanetId, instanceId, timestamp)
				.changes must beEqualTo(List(
          PlanetOrbitOrdered(shipId, nonHostilePlanetId, instanceId, timestamp)))
		}

		"move to star offset" in new BuiltShipScope {
			val starId = StarId(UUID.randomUUID)
			val offset = StarOffset(0, 0)
			ship
        .moveTo(starId, offset, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipCoordinatesOrdered(shipId, starId, offset, instanceId, timestamp)))
		}

    "move to star" in new BuiltShipScope {
      val starId = StarId(UUID.randomUUID)
      ship
        .moveTo(starId, instanceId, timestamp)
        .changes must beEqualTo(List(
          SolarSystemEntryOrdered(shipId, starId, instanceId, timestamp)))
    }

    "halt" in new BuiltShipScope {
      ship
        .halt(instanceId, timestamp)
        .changes must beEqualTo(List(
          ShipHaltOrdered(shipId, instanceId, timestamp)))
    }

    "decommission" in new BuiltShipScope {
      val decommissionedEvent = ShipDecommissioned(shipId, instanceId, timestamp)
      ship
        .decommission(instanceId, timestamp)
        .changes must beEqualTo(List(
          decommissionedEvent,
          ShipDestroyed(shipId, decommissionedEvent, instanceId, timestamp)))
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
		val shipBuildEvent = ShipBuildCommissioned(PlanetId(UUID.randomUUID), ShipSpecificationId(UUID.randomUUID), shipId, instanceId, timestamp)
		val ship = Ship.finalizeBuild(shipBuildEvent, instanceId, timestamp).markCommitted
	}
}
