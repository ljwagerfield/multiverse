package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.speciesAssets.{SpeciesAssetsDefined, SpeciesAssets, SpeciesAssetsId}
import io.multiverse.domain.shared.Hash
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Species flag background vector specification.
 */
class SpeciesAssetsSpec extends Specification {
  "species assets" should {
    "be defined" in new InstanceScope {
      val speciesAssetsId = SpeciesAssetsId(UUID.randomUUID())
      val assetsHash = Hash.empty

      SpeciesAssets
        .define(speciesAssetsId, assetsHash, instanceId, timestamp)
        .changes must beEqualTo(List(
          SpeciesAssetsDefined(instanceId, timestamp, speciesAssetsId, assetsHash)))
    }
  }
}
