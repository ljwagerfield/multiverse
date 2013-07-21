package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.planetOwnership.{PlanetAbandoned, PlanetColonized, PlanetOwnership}
import io.multiverse.domain.aggregates.ship.PlanetColonizationOrdered
import io.multiverse.domain.aggregates.ship.ShipId
import io.multiverse.domain.aggregates.solarSystem.PlanetId
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Planet ownership specification.
 */
class PlanetOwnershipSpec extends Specification {
  "planet" should {
    "have an initial ownership" in new PlanetOwnershipScope {
      PlanetOwnership.init(planetId)
        .changes must beEmpty
    }

    "be colonized by a ship" in new InitializedPlanetOwnershipScope {
      val colonizationOrder = PlanetColonizationOrdered(ShipId(UUID.randomUUID), planetId, instanceId, timestamp)
      val invalidColonizationOrder = PlanetColonizationOrdered(ShipId(UUID.randomUUID), PlanetId(UUID.randomUUID), instanceId, timestamp)

      planetOwnership
        .colonize(colonizationOrder, instanceId, timestamp)
        .changes must beEqualTo(List(
          PlanetColonized(planetId, colonizationOrder, instanceId, timestamp)))

      planetOwnership
        .colonize(invalidColonizationOrder, instanceId, timestamp) must throwA[Exception]
    }

    "be abandoned by its inhabitants" in new ColonizedPlanetOwnershipScope {
      colonizedOwnership
        .abandon(instanceId, timestamp)
        .changes must beEqualTo(List(
          PlanetAbandoned(planetId, instanceId, timestamp)))
    }

    "support idempotent abandonment" in new ColonizedPlanetOwnershipScope {
      colonizedOwnership
        .abandon(instanceId, timestamp)
        .markCommitted
        .abandon(instanceId, timestamp + 1)
        .changes must beEmpty
    }

    "be superfluously abandoned by no inhabitants" in new InitializedPlanetOwnershipScope {
      planetOwnership
        .abandon(instanceId, timestamp)
        .changes must beEmpty
    }
  }

  /**
   * Predefined test values for planet ownership.
   */
  trait PlanetOwnershipScope extends InstanceScope {
    val planetId = PlanetId(UUID.randomUUID)
  }

  /**
   * Predefined test values for initialized planet ownership.
   */
  trait InitializedPlanetOwnershipScope extends PlanetOwnershipScope {
    val planetOwnership = PlanetOwnership.init(planetId)
  }
  
  /**
   * Predefined test values for colonized planet ownership.
   */
  trait ColonizedPlanetOwnershipScope extends InitializedPlanetOwnershipScope {
    val colonizationOrder = PlanetColonizationOrdered(ShipId(UUID.randomUUID), planetId, instanceId, timestamp)
    val colonizedOwnership = planetOwnership.colonize(colonizationOrder, instanceId, timestamp).markCommitted
  }
}
