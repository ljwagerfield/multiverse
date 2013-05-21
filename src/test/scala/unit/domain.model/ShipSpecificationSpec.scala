package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.shipAssets.ShipAssetsId
import io.multiverse.domain.model.shipResearch.ArmourId
import io.multiverse.domain.model.shipResearch.ColonizingUnitId
import io.multiverse.domain.model.shipResearch.EngineId
import io.multiverse.domain.model.shipResearch.ShieldId
import io.multiverse.domain.model.shipResearch.WeaponId
import io.multiverse.domain.model.shipSpecification.QuantifiedColonizingUnitId
import io.multiverse.domain.model.shipSpecification.QuantifiedWeaponId
import io.multiverse.domain.model.shipSpecification.ShipComposition
import io.multiverse.domain.model.shipSpecification.ShipSize
import io.multiverse.domain.model.shipSpecification.ShipSpecification
import io.multiverse.domain.model.shipSpecification.ShipSpecificationId
import io.multiverse.domain.model.shipSpecification.ShipSpecified
import io.multiverse.domain.model.species.SpeciesId
import io.multiverse.domain.shared.ShortAlphanumericName
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Ship specification specification.
 */
class ShipSpecificationSpec extends Specification {
	"ship specification" should {
		"be defined" in new ShipSpecificationScope {
			ShipSpecification.define(shipSpecificationId, speciesId, shipAssetsId, size, name, composition, instanceId, timestamp)
				.changes must contain(ShipSpecified(instanceId, timestamp, shipSpecificationId, speciesId, shipAssetsId, size, name, composition))
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
