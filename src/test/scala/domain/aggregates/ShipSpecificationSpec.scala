package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.shipAssets.ShipAssetsId
import io.multiverse.domain.aggregates.shipResearch.ArmourId
import io.multiverse.domain.aggregates.shipResearch.ColonizingUnitId
import io.multiverse.domain.aggregates.shipResearch.EngineId
import io.multiverse.domain.aggregates.shipResearch.ShieldId
import io.multiverse.domain.aggregates.shipResearch.WeaponId
import io.multiverse.domain.aggregates.shipSpecification.QuantifiedColonizingUnitId
import io.multiverse.domain.aggregates.shipSpecification.QuantifiedWeaponId
import io.multiverse.domain.aggregates.shipSpecification.ShipComposition
import io.multiverse.domain.aggregates.shipSpecification.ShipSize
import io.multiverse.domain.aggregates.shipSpecification.ShipSpecification
import io.multiverse.domain.aggregates.shipSpecification.ShipSpecificationId
import io.multiverse.domain.aggregates.shipSpecification.ShipSpecified
import io.multiverse.domain.aggregates.species.SpeciesId
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.aggregates.common.ShortAlphanumericName

/**
 * Ship specification specification.
 */
class ShipSpecificationSpec extends Specification {
	"ship specification" should {
		"be defined" in new ShipSpecificationScope {
			ShipSpecification
        .define(shipSpecificationId, speciesId, shipAssetsId, size, name, composition, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipSpecified(instanceId, timestamp, shipSpecificationId, speciesId, shipAssetsId, size, name, composition)))
		}
	}

	/**
	 * Predefined test values for ship specification.
	 */
	trait ShipSpecificationScope extends InstanceScope {
		val shipSpecificationId = ShipSpecificationId(UUID.randomUUID)
		val speciesId = SpeciesId(UUID.randomUUID)
		val shipAssetsId = ShipAssetsId(UUID.randomUUID)
		val size = ShipSize(1)
		val name = ShortAlphanumericName("x500")
		val composition = ShipComposition(
			EngineId(UUID.randomUUID),
			ArmourId(UUID.randomUUID),
			ShieldId(UUID.randomUUID),
			Set(QuantifiedWeaponId(WeaponId(UUID.randomUUID), 1), QuantifiedWeaponId(WeaponId(UUID.randomUUID), 2)),
			Set(QuantifiedColonizingUnitId(ColonizingUnitId(UUID.randomUUID), 2), QuantifiedColonizingUnitId(ColonizingUnitId(UUID.randomUUID), 3)))
	}
}
