package domain.model

import _root_.baseSpecifications.InstanceScope
import io.multiverse.domain.model.shipAssets.{ShipAssetsDefined, ShipAssets, ShipAssetsId}
import io.multiverse.domain.model.shipSpecification.ShipSize
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.model.common.values.Hash

/**
 * Ship assets specification.
 */
class ShipAssetsSpec extends Specification {
	"ship assets" should {
		"be defined" in new InstanceScope {
			val shipAssetsId = ShipAssetsId(UUID.randomUUID)
			val size = ShipSize(1)
			val assetsHash = Hash.empty
			ShipAssets
        .define(shipAssetsId, size, assetsHash, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipAssetsDefined(shipAssetsId, size, assetsHash, instanceId, timestamp)))
		}
	}
}
