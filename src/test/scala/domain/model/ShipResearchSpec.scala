package domain.model

import baseSpecifications.CommandCombinators.{aggregateToTestChain, chainToTestChain}
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.commands.CommandCombinators.aggregateToChain
import io.multiverse.domain.model.shipResearch.{ShipResearchFocused, ProductionResearchFocus, ComponentResearchFocus, ShipResearch}
import io.multiverse.domain.model.species.SpeciesId
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.model.shipResearch.commands.FocusShipResearch

/**
 * Ship research specification.
 */
class ShipResearchSpec extends Specification {
	"ship research" should {
		"support initialization" in new ShipResearchScope {
      (ShipResearch(speciesId)
				yields Nil)
		}

		"support being focused" in new ShipResearchScope {
			val componentResearch = ComponentResearchFocus(20, 20, 20, 20, 20)
			val productionResearch = ProductionResearchFocus(25, 25, 25, 25)

      (ShipResearch(speciesId)
				after FocusShipResearch(speciesId, componentResearch, productionResearch, instanceId, timestamp)
				yields ShipResearchFocused(speciesId, componentResearch, productionResearch, instanceId, timestamp))
		}

		"support being refocused" in new ShipResearchScope {
			val componentResearch = ComponentResearchFocus(20, 20, 20, 20, 20)
			val productionResearch = ProductionResearchFocus(25, 25, 25, 25)
			val componentResearch2 = ComponentResearchFocus(30, 10, 20, 20, 20)
			val productionResearch2 = ProductionResearchFocus(40, 10, 25, 25)

      (ShipResearch(speciesId)
				after FocusShipResearch(speciesId, componentResearch, productionResearch, instanceId, timestamp)
				after FocusShipResearch(speciesId, componentResearch2, productionResearch2, instanceId, timestamp)
				yields List(
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
