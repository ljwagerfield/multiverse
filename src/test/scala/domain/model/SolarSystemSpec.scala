package domain.model

import _root_.baseSpecifications.InstanceScope
import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.solarSystem.PlanetNamed
import io.multiverse.domain.model.solarSystem.SolarSystem
import io.multiverse.domain.model.solarSystem.SolarSystemCreated
import io.multiverse.domain.model.solarSystem.StarId
import io.multiverse.domain.model.solarSystem.StarNameDuplicateRenamed
import io.multiverse.domain.model.solarSystem.StarNamed
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.model.common.values.{ShortAlphanumericName, ShortAlphabeticName}

/**
 * Solar system specification.
 */
class SolarSystemSpec extends Specification {
  "new solar system" should {
    "be creatable in a new galaxy (no near stars)" in new SolarSystemScope {
      SolarSystem
        .create(starId, noNearStars, twoPlanets, instanceId, timestamp)
        .changes must beEqualTo(List(
          SolarSystemCreated(starId, noNearStars, twoPlanets, instanceId, timestamp)))
    }

    "be creatable in an existing galaxy near 1 star" in new SolarSystemScope {
      SolarSystem
        .create(starId, oneNearStar, twoPlanets, instanceId, timestamp)
        .changes must beEqualTo(List(
          SolarSystemCreated(starId, oneNearStar, twoPlanets, instanceId, timestamp)))
    }

    "be creatable in an existing galaxy near up-to 4 stars" in new SolarSystemScope {
      SolarSystem
        .create(starId, fourNearStars, twoPlanets, instanceId, timestamp)
        .changes must beEqualTo(List(
          SolarSystemCreated(starId, fourNearStars, twoPlanets, instanceId, timestamp)))

      SolarSystem
        .create(starId, fourNearStars + StarId(UUID.randomUUID), twoPlanets, instanceId, timestamp) must throwA[Exception]
    }

    "not be near to itself" in new SolarSystemScope {
      val nearStars = Set(StarId(UUID.randomUUID), starId, StarId(UUID.randomUUID), StarId(UUID.randomUUID))
      SolarSystem
        .create(starId, nearStars, twoPlanets, instanceId, timestamp) must throwA[Exception]
    }

    "have between 2 and 6 planets" in new SolarSystemScope {
      SolarSystem
        .create(starId, noNearStars, twoPlanets, instanceId, timestamp)
        .changes must beEqualTo(List(
          SolarSystemCreated(starId, noNearStars, twoPlanets, instanceId, timestamp)))

      SolarSystem
        .create(starId, noNearStars, sixPlanets, instanceId, timestamp)
        .changes must beEqualTo(List(
          SolarSystemCreated(starId, noNearStars, sixPlanets, instanceId, timestamp)))

      SolarSystem
        .create(starId, noNearStars, sixPlanets + PlanetId(UUID.randomUUID), instanceId, timestamp) must throwA[Exception]

      SolarSystem
        .create(starId, noNearStars, Set(PlanetId(UUID.randomUUID)), instanceId, timestamp) must throwA[Exception]
    }
  }

  "solar system" should {
    "be nameable" in new ExistingSolarSystemScope {
      val firstExplicitName = ShortAlphabeticName("Sun")
      solarSystem
        .nameStar(firstExplicitName, instanceId, timestamp)
        .changes must beEqualTo(List(
          StarNamed(starId, firstExplicitName, instanceId, timestamp)))
    }

    "be rename-able" in new ExistingSolarSystemScope {
      val firstExplicitName = ShortAlphabeticName("Sun")
      val secondExplicitName = ShortAlphabeticName("Origan")
      solarSystem
        .nameStar(firstExplicitName, instanceId, timestamp)
        .nameStar(secondExplicitName, instanceId, timestamp)
        .changes must beEqualTo(List(
          StarNamed(starId, firstExplicitName, instanceId, timestamp),
          StarNamed(starId, secondExplicitName, instanceId, timestamp)))
    }
  }

  "solar system with duplicate name" should {
    "be resolvable" in new ExistingSolarSystemScope {
      val conflictingStarId = StarId(UUID.randomUUID)
      val duplicateName = ShortAlphabeticName("Sun")
      val resolvedName = ShortAlphabeticName("Origan")
      solarSystem
        .nameStar(duplicateName, instanceId, timestamp)
        .resolveDuplicateStarName(conflictingStarId, resolvedName, instanceId, timestamp)
        .changes must beEqualTo(List(
          StarNamed(starId, duplicateName, instanceId, timestamp),
          StarNameDuplicateRenamed(starId, conflictingStarId, resolvedName, instanceId, timestamp)))
    }
  }

  "planet" should {
    "be nameable" in new ExistingSolarSystemScope {
      val firstPlanetName = ShortAlphanumericName("Earth")
      solarSystem
        .namePlanet(firstPlanetId, firstPlanetName, instanceId, timestamp)
        .changes must beEqualTo(List(
          PlanetNamed(starId, firstPlanetId, firstPlanetName, instanceId, timestamp)))
    }

    "be rename-able" in new NamedFirstPlanetScope {
      val newPlanetName = ShortAlphanumericName("Mars")
      solarSystemWithNamedPlanet
        .namePlanet(firstPlanetId, newPlanetName, instanceId, timestamp)
        .changes must beEqualTo(List(
          PlanetNamed(starId, firstPlanetId, newPlanetName, instanceId, timestamp)))
    }

    "have unique name" in new NamedFirstPlanetScope {
      // Name second planet with first planet's name.
      solarSystemWithNamedPlanet
        .namePlanet(secondPlanetId, firstPlanetName, instanceId, timestamp) must throwA[Exception]
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
    val solarSystem = SolarSystem.create(starId, noNearStars, sixPlanets, instanceId, timestamp).markCommitted
  }

  /**
   * Predefined test values for existing solar systems with a named planet.
   */
  trait NamedFirstPlanetScope extends ExistingSolarSystemScope {
    val firstPlanetName = ShortAlphanumericName("Earth")
    val solarSystemWithNamedPlanet = solarSystem.namePlanet(firstPlanetId, firstPlanetName, instanceId, timestamp).markCommitted
  }
}
