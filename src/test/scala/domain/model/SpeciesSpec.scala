package domain.model

import _root_.baseSpecifications.InstanceScope
import io.multiverse.domain.model.resource.ResourceId
import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.species._
import io.multiverse.domain.model.speciesAssets.SpeciesAssetsId
import io.multiverse.domain.model.speciesFlagBackgroundVector.SpeciesFlagBackgroundVectorId
import io.multiverse.domain.model.speciesFlagEmblemVector.SpeciesFlagEmblemVectorId
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.model.common.values.ShortAlphabeticName

/**
 * Species specification.
 */
class SpeciesSpec extends Specification {
  "species" should {
    "be evolved" in new SpeciesScope {
      Species
        .evolve(speciesId, name, flag, speciesAssetsId, planetId, characteristics, instanceId, timestamp)
        .changes must beEqualTo(List(
          SpeciesEvolved(speciesId, name, flag, speciesAssetsId, planetId, characteristics, instanceId, timestamp)))
    }
  }

  "species with duplicate name" should {
    "be resolved" in new EvolvedSpeciesScope {
      val conflictingSpeciesId = SpeciesId(UUID.randomUUID)
      val newName = ShortAlphabeticName("Martian")
      species
        .resolveDuplicateSpeciesName(conflictingSpeciesId, newName, instanceId, timestamp)
        .changes must beEqualTo(List(
          SpeciesNameDuplicateRenamed(speciesId, conflictingSpeciesId, newName, instanceId, timestamp)))
    }
  }

  /**
   * Predefined test values for species.
   */
  trait SpeciesScope extends InstanceScope {
    val speciesId = SpeciesId(UUID.randomUUID)
    val name = ShortAlphabeticName("Human")
    val flag = SpeciesFlag(0, 0, 0, SpeciesFlagBackgroundVectorId(UUID.randomUUID), SpeciesFlagEmblemVectorId(UUID.randomUUID))
    val speciesAssetsId = SpeciesAssetsId(UUID.randomUUID)
    val planetId = PlanetId(UUID.randomUUID)
    val characteristics = SpeciesCharacteristics(
      SpeciesBonus(0, 0),
      0,
      SpeciesCharacteristics.minTolerance,
      List(ResourceDependency(ResourceId(UUID.randomUUID), 1), ResourceDependency(ResourceId(UUID.randomUUID), 5)),
      List(BonusResourceDependency(ResourceDependency(ResourceId(UUID.randomUUID), 10), SpeciesBonus(0, 0))))
  }

  /**
   * Predefined test values for evolved species.
   */
  trait EvolvedSpeciesScope extends SpeciesScope {
    val species = Species.evolve(speciesId, name, flag, speciesAssetsId, planetId, characteristics, instanceId, timestamp).markCommitted
  }
}
