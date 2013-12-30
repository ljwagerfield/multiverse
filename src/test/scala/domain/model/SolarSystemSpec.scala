package domain.model

import baseSpecifications.CommandCombinators.{chainToTestChain, headCommandToTestChain}
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.commands.CommandCombinators.headCommandToChain
import io.multiverse.domain.model.common.commands.Commit
import io.multiverse.domain.model.common.values.{ShortAlphanumericName, ShortAlphabeticName}
import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.solarSystem.PlanetNamed
import io.multiverse.domain.model.solarSystem.SolarSystemCreated
import io.multiverse.domain.model.solarSystem.StarId
import io.multiverse.domain.model.solarSystem.StarNameDuplicateRenamed
import io.multiverse.domain.model.solarSystem.StarNamed
import io.multiverse.domain.model.solarSystem.commands.{ResolveDuplicateStarName, NameStar, NamePlanet, CreateSolarSystem}
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Solar system specification.
 */
class SolarSystemSpec extends Specification {
  "new solar system" should {
    "be creatable in a new galaxy (no near stars)" in new SolarSystemScope {
      (CreateSolarSystem(starId, noNearStars, twoPlanets, instanceId, timestamp)
        yields SolarSystemCreated(starId, noNearStars, twoPlanets, instanceId, timestamp))
    }

    "be creatable in an existing galaxy near 1 star" in new SolarSystemScope {
      (CreateSolarSystem(starId, oneNearStar, twoPlanets, instanceId, timestamp)
        yields SolarSystemCreated(starId, oneNearStar, twoPlanets, instanceId, timestamp))
    }

    "be creatable in an existing galaxy near up-to 4 stars" in new SolarSystemScope {
      (CreateSolarSystem(starId, fourNearStars, twoPlanets, instanceId, timestamp)
        yields SolarSystemCreated(starId, fourNearStars, twoPlanets, instanceId, timestamp))

      (CreateSolarSystem(starId, fourNearStars + StarId(UUID.randomUUID), twoPlanets, instanceId, timestamp)
        must throwA[Exception])
    }

    "not be near to itself" in new SolarSystemScope {
      val nearStars = Set(StarId(UUID.randomUUID), starId, StarId(UUID.randomUUID), StarId(UUID.randomUUID))
      (CreateSolarSystem(starId, nearStars, twoPlanets, instanceId, timestamp)
        must throwA[Exception])
    }

    "support as few as 2 planets" in new SolarSystemScope {
      (CreateSolarSystem(starId, noNearStars, Set(PlanetId(UUID.randomUUID)), instanceId, timestamp)
        must throwA[Exception])

      (CreateSolarSystem(starId, noNearStars, twoPlanets, instanceId, timestamp)
        yields SolarSystemCreated(starId, noNearStars, twoPlanets, instanceId, timestamp))
    }

    "support up to 6 planets" in new SolarSystemScope {
      (CreateSolarSystem(starId, noNearStars, sixPlanets, instanceId, timestamp)
        yields SolarSystemCreated(starId, noNearStars, sixPlanets, instanceId, timestamp))

      (CreateSolarSystem(starId, noNearStars, sixPlanets + PlanetId(UUID.randomUUID), instanceId, timestamp)
        must throwA[Exception])
    }
  }

  "solar system" should {
    "be nameable" in new ExistingSolarSystemScope {
      val firstExplicitName = ShortAlphabeticName("Sun")
      (solarSystem
        after NameStar(starId, firstExplicitName, instanceId, timestamp)
        yields StarNamed(starId, firstExplicitName, instanceId, timestamp))
    }

    "be rename-able" in new ExistingSolarSystemScope {
      val firstExplicitName = ShortAlphabeticName("Sun")
      val secondExplicitName = ShortAlphabeticName("Origan")
      (solarSystem
        after NameStar(starId, firstExplicitName, instanceId, timestamp)
        after NameStar(starId, secondExplicitName, instanceId, timestamp)
        yields List(
          StarNamed(starId, firstExplicitName, instanceId, timestamp),
          StarNamed(starId, secondExplicitName, instanceId, timestamp)))
    }
  }

  "solar system with duplicate name" should {
    "be resolvable" in new ExistingSolarSystemScope {
      val conflictingStarId = StarId(UUID.randomUUID)
      val duplicateName = ShortAlphabeticName("Sun")
      val resolvedName = ShortAlphabeticName("Origan")
      (solarSystem
        after NameStar(starId, duplicateName, instanceId, timestamp)
        after ResolveDuplicateStarName(starId, conflictingStarId, resolvedName, instanceId, timestamp)
        yields List(
          StarNamed(starId, duplicateName, instanceId, timestamp),
          StarNameDuplicateRenamed(starId, conflictingStarId, resolvedName, instanceId, timestamp)))
    }
  }

  "planet" should {
    "be nameable" in new ExistingSolarSystemScope {
      val firstPlanetName = ShortAlphanumericName("Earth")
      (solarSystem
        after NamePlanet(starId, firstPlanetId, firstPlanetName, instanceId, timestamp)
        yields PlanetNamed(starId, firstPlanetId, firstPlanetName, instanceId, timestamp))
    }

    "be rename-able" in new NamedFirstPlanetScope {
      val newPlanetName = ShortAlphanumericName("Mars")
      (solarSystemWithNamedPlanet
        after NamePlanet(starId, firstPlanetId, newPlanetName, instanceId, timestamp)
        yields PlanetNamed(starId, firstPlanetId, newPlanetName, instanceId, timestamp))
    }

    "have unique name" in new NamedFirstPlanetScope {
      (solarSystemWithNamedPlanet
        cannot NamePlanet(starId, secondPlanetId, firstPlanetName, instanceId, timestamp)
        because NamePlanet.UniquePlanetName)
    }
  }

  /**
   * Predefined test values for solar systems.
   */
  trait SolarSystemScope extends InstanceScope {
    val starId = StarId(UUID.randomUUID)
    val noNearStars = Set.empty[StarId]
    val oneNearStar = Set(StarId(UUID.randomUUID))
    val fourNearStars = Set(StarId(UUID.randomUUID), StarId(UUID.randomUUID), StarId(UUID.randomUUID), StarId(UUID.randomUUID))
    val firstPlanetId = PlanetId(UUID.randomUUID)
    val secondPlanetId = PlanetId(UUID.randomUUID)
    val twoPlanets = Set(firstPlanetId, secondPlanetId)
    val sixPlanets = twoPlanets ++ Set(PlanetId(UUID.randomUUID), PlanetId(UUID.randomUUID), PlanetId(UUID.randomUUID), PlanetId(UUID.randomUUID))
  }

  /**
   * Predefined test values for existing solar systems.
   */
  trait ExistingSolarSystemScope extends SolarSystemScope {
    val solarSystem = CreateSolarSystem(starId, noNearStars, sixPlanets, instanceId, timestamp) after Commit()
  }

  /**
   * Predefined test values for existing solar systems with a named planet.
   */
  trait NamedFirstPlanetScope extends ExistingSolarSystemScope {
    val firstPlanetName = ShortAlphanumericName("Earth")
    val solarSystemWithNamedPlanet = (solarSystem
      after NamePlanet(starId, firstPlanetId, firstPlanetName, instanceId, timestamp)
      after Commit())
  }
}
