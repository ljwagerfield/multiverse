package unit.domain.model

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.speciesAssets.{SpeciesAssetsDefined, SpeciesAssets, SpeciesAssetsId}
import io.multiverse.domain.shared.Hash
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Species flag background vector specification.
 */
class SpeciesAssetsSpec extends Specification {
  val timestamp = 0
  val instanceId = InstanceId(UUID.randomUUID())

  "species assets" should {
    "be defined" in {
      val speciesAssetsId = SpeciesAssetsId(UUID.randomUUID())
      val assetsHash = Hash.empty
      val speciesAssets = SpeciesAssets.define(speciesAssetsId, assetsHash, instanceId, timestamp)

      speciesAssets.changes must contain(SpeciesAssetsDefined(instanceId, timestamp, speciesAssetsId, assetsHash))
    }
  }
}
