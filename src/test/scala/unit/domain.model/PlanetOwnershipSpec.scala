package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.planetOwnership.{PlanetAbandoned, PlanetColonized, PlanetOwnership}
import io.multiverse.domain.model.ship.PlanetColonizationOrdered
import io.multiverse.domain.model.ship.ShipId
import io.multiverse.domain.model.solarSystem.PlanetId
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
      val colonizationOrder = PlanetColonizationOrdered(instanceId, timestamp, ShipId(UUID.randomUUID), planetId)
      val invalidColonizationOrder = PlanetColonizationOrdered(instanceId, timestamp, ShipId(UUID.randomUUID), PlanetId(UUID.randomUUID))

      planetOwnership.colonize(colonizationOrder, instanceId, timestamp)
        .changes must contain(PlanetColonized(instanceId, timestamp, planetId, colonizationOrder))

      planetOwnership.colonize(invalidColonizationOrder, instanceId, timestamp) must throwA[Exception]
    }

    "be abandoned by its inhabitants" in new InitializedPlanetOwnershipScope {
      val colonizationOrder = PlanetColonizationOrdered(instanceId, timestamp, ShipId(UUID.randomUUID), planetId)

      planetOwnership
        .colonize(colonizationOrder, instanceId, timestamp)
        .abandon(instanceId, timestamp)
        .changes must contain(PlanetAbandoned(instanceId, timestamp, planetId))
    }

    "be superfluously abandoned by no inhabitants" in new InitializedPlanetOwnershipScope {
      planetOwnership.abandon(instanceId, timestamp)
        .changes must contain(PlanetAbandoned(instanceId, timestamp, planetId))
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
}
