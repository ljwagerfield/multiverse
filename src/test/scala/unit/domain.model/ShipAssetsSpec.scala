package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.shipAssets.{ShipAssetsDefined, ShipAssets, ShipAssetsId}
import io.multiverse.domain.model.shipSpecification.ShipSize
import io.multiverse.domain.shared.Hash
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
			ShipAssets
        .define(shipAssetsId, size, assetsHash, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipAssetsDefined(instanceId, timestamp, shipAssetsId, size, assetsHash)))
		}
	}
}
