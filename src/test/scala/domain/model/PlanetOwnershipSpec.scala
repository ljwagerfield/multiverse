package domain.model

import baseSpecifications.CommandCombinators.{aggregateToTestChain, chainToTestChain}
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.commands.CommandCombinators.aggregateToChain
import io.multiverse.domain.model.common.commands.Commit
import io.multiverse.domain.model.planetOwnership.commands.Abandon
import io.multiverse.domain.model.planetOwnership.commands.Colonize
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
      (PlanetOwnership(planetId)
        yields Nil)
    }

    "be colonized by a ship" in new InitializedPlanetOwnershipScope {
      val colonizationOrder = PlanetColonizationOrdered(ShipId(UUID.randomUUID), planetId, instanceId, timestamp)
      val invalidColonizationOrder = PlanetColonizationOrdered(ShipId(UUID.randomUUID), PlanetId(UUID.randomUUID), instanceId, timestamp)

      (Colonize(planetId, invalidColonizationOrder, instanceId, timestamp)
        must throwA[Exception])

      (planetOwnership
        after Colonize(planetId, colonizationOrder, instanceId, timestamp)
        yields PlanetColonized(planetId, colonizationOrder, instanceId, timestamp))
    }

    "be abandoned by its inhabitants" in new ColonizedPlanetOwnershipScope {
      (colonizedOwnership
        after Abandon(planetId, instanceId, timestamp)
        yields PlanetAbandoned(planetId, instanceId, timestamp))
    }

    "support idempotent abandonment" in new ColonizedPlanetOwnershipScope {
      (colonizedOwnership
        after Abandon(planetId, instanceId, timestamp)
        after Commit()
        after Abandon(planetId, instanceId, timestamp + 1)
        yields Nil)
    }

    "be superfluously abandoned by no inhabitants" in new InitializedPlanetOwnershipScope {
      (planetOwnership
        after Abandon(planetId, instanceId, timestamp)
        yields Nil)
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
    val planetOwnership = PlanetOwnership(planetId)
  }
  
  /**
   * Predefined test values for colonized planet ownership.
   */
  trait ColonizedPlanetOwnershipScope extends InitializedPlanetOwnershipScope {
    val colonizationOrder = PlanetColonizationOrdered(ShipId(UUID.randomUUID), planetId, instanceId, timestamp)
    val colonizedOwnership = planetOwnership after Colonize(planetId, colonizationOrder, instanceId, timestamp) after Commit()
  }
}
