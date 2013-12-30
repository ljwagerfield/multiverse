package domain.model

import baseSpecifications.CommandCombinators.{chainToTestChain, headCommandToTestChain}
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.commands.CommandCombinators.headCommandToChain
import io.multiverse.domain.model.common.commands.Commit
import io.multiverse.domain.model.planetIndustry.ShipBuildCommissioned
import io.multiverse.domain.model.ship.commands.{Decommission, HaltShip, MoveToEntryWormhole, MoveToOffset, OrbitPlanet, Colonize, AttackPlanet, AttackShip, FinalizeShipBuild}
import io.multiverse.domain.model.ship.{ShipDestroyed, ShipDecommissioned, ShipHaltOrdered, ShipCoordinatesOrdered, StarOffset, PlanetOrbitOrdered, PlanetColonizationOrdered, PlanetAttackOrdered, ShipAttackOrdered, ShipBuilt, SolarSystemEntryOrdered, ShipId}
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
      (FinalizeShipBuild(shipBuildEvent, instanceId, timestamp)
				yields ShipBuilt(shipId, shipBuildEvent, instanceId, timestamp))
		}
	}

	"ship" should {
		"attack non-friendly ship" in new BuiltShipScope {
			val nonFriendlyShipId = ShipId(UUID.randomUUID)
      (ship
        after AttackShip(shipId, nonFriendlyShipId, instanceId, timestamp)
				yields ShipAttackOrdered(shipId, nonFriendlyShipId, instanceId, timestamp))
		}

		"not attack itself" in new BuiltShipScope {
      (AttackShip(shipId, shipId, instanceId, timestamp)
        must throwA[Exception])
		}

		"attack non-friendly planet" in new BuiltShipScope {
			val nonFriendlyPlanetId = PlanetId(UUID.randomUUID)
      (ship
        after AttackPlanet(shipId, nonFriendlyPlanetId, instanceId, timestamp)
				yields PlanetAttackOrdered(shipId, nonFriendlyPlanetId, instanceId, timestamp))
		}

		"colonize vacant planet" in new BuiltShipScope {
			val vacantPlanetId = PlanetId(UUID.randomUUID)
      (ship
        after Colonize(shipId, vacantPlanetId, instanceId, timestamp)
				yields PlanetColonizationOrdered(shipId, vacantPlanetId, instanceId, timestamp))
		}

		"orbit non-hostile planet" in new BuiltShipScope {
			val nonHostilePlanetId = PlanetId(UUID.randomUUID)
      (ship
        after OrbitPlanet(shipId, nonHostilePlanetId, instanceId, timestamp)
				yields PlanetOrbitOrdered(shipId, nonHostilePlanetId, instanceId, timestamp))
		}

		"move to star offset" in new BuiltShipScope {
			val starId = StarId(UUID.randomUUID)
			val offset = StarOffset(0, 0)
      (ship
        after MoveToOffset(shipId, starId, offset, instanceId, timestamp)
				yields ShipCoordinatesOrdered(shipId, starId, offset, instanceId, timestamp))
		}

    "move to star" in new BuiltShipScope {
      val starId = StarId(UUID.randomUUID)
      (ship
        after MoveToEntryWormhole(shipId, starId, instanceId, timestamp)
        yields SolarSystemEntryOrdered(shipId, starId, instanceId, timestamp))
    }

    "halt" in new BuiltShipScope {
      (ship
        after HaltShip(shipId, instanceId, timestamp)
        yields ShipHaltOrdered(shipId, instanceId, timestamp))
    }

    "decommission" in new BuiltShipScope {
      val decommissionedEvent = ShipDecommissioned(shipId, instanceId, timestamp)
      (ship
        after Decommission(shipId, instanceId, timestamp)
        yields List(
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
		val ship = FinalizeShipBuild(shipBuildEvent, instanceId, timestamp) after Commit()
	}
}
