package domain.model

import baseSpecifications.CommandCombinators.headCommandToTestChain
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.speciesAssets.commands.DefineSpeciesAssets
import io.multiverse.domain.model.speciesAssets.{SpeciesAssetsDefined, SpeciesAssetsId}
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

      (DefineSpeciesAssets(speciesAssetsId, assetsHash, instanceId, timestamp)
        yields SpeciesAssetsDefined(speciesAssetsId, assetsHash, instanceId, timestamp))
    }
  }
}
