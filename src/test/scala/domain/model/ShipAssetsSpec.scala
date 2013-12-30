package domain.model

import baseSpecifications.CommandCombinators.headCommandToTestChain
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.shipAssets.commands.DefineShipAssets
import io.multiverse.domain.model.shipAssets.{ShipAssetsDefined, ShipAssetsId}
import io.multiverse.domain.model.shipSpecification.ShipSize
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Ship assets specification.
 */
class ShipAssetsSpec extends Specification {
	"ship assets" should {
		"be defined" in new InstanceScope {
			val shipAssetsId = ShipAssetsId(UUID.randomUUID)
			val size = ShipSize(1)
			val assetsHash = Hash.empty
      (DefineShipAssets(shipAssetsId, size, assetsHash, instanceId, timestamp)
				yields ShipAssetsDefined(shipAssetsId, size, assetsHash, instanceId, timestamp))
		}
	}
}
