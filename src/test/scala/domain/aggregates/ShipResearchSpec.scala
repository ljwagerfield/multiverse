package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.shipResearch.{ShipResearchFocused, ProductionResearchFocus, ComponentResearchFocus, ShipResearch}
import io.multiverse.domain.aggregates.species.SpeciesId
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Ship research specification.
 */
class ShipResearchSpec extends Specification {
	"ship research" should {
		"support initialization" in new ShipResearchScope {
			ShipResearch
        .init(speciesId)
				.changes must beEmpty
		}

		"support being focused" in new ShipResearchScope {
			val componentResearch = ComponentResearchFocus(20, 20, 20, 20, 20)
			val productionResearch = ProductionResearchFocus(25, 25, 25, 25)

			ShipResearch
				.init(speciesId)
				.focus(componentResearch, productionResearch, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipResearchFocused(speciesId, componentResearch, productionResearch, instanceId, timestamp)))
		}

		"support being refocused" in new ShipResearchScope {
			val componentResearch = ComponentResearchFocus(20, 20, 20, 20, 20)
			val productionResearch = ProductionResearchFocus(25, 25, 25, 25)
			val componentResearch2 = ComponentResearchFocus(30, 10, 20, 20, 20)
			val productionResearch2 = ProductionResearchFocus(40, 10, 25, 25)

			ShipResearch
				.init(speciesId)
				.focus(componentResearch, productionResearch, instanceId, timestamp)
				.focus(componentResearch2, productionResearch2, instanceId, timestamp)
				.changes must beEqualTo(List(
          ShipResearchFocused(speciesId, componentResearch, productionResearch, instanceId, timestamp),
          ShipResearchFocused(speciesId, componentResearch2, productionResearch2, instanceId, timestamp)))
		}
	}

	/**
	 * Predefined test values for ship research.
	 */
	trait ShipResearchScope extends InstanceScope {
		val speciesId = SpeciesId(UUID.randomUUID)
	}
}
