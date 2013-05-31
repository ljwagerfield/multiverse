package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.speciesAssets.{SpeciesAssetsDefined, SpeciesAssets, SpeciesAssetsId}
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.aggregates.common.Hash

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
