package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.shipAssets.{ShipAssetsDefined, ShipAssets, ShipAssetsId}
import io.multiverse.domain.aggregates.shipSpecification.ShipSize
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.aggregates.common.Hash

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
          ShipAssetsDefined(instanceId, timestamp, shipAssetsId, size, assetsHash)))
		}
	}
}
