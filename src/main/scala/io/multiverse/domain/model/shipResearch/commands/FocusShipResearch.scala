package io.multiverse.domain.model.shipResearch.commands

import io.multiverse.domain.model.shipResearch.{ShipResearchFocused, ShipResearch, ProductionResearchFocus, ComponentResearchFocus, ShipResearchCommand}
import io.multiverse.domain.model.species.SpeciesId
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.StaticTailCommand

/**
 * Focuses ship research for the species.
 * @param speciesId Species whose research is being focused.
 * @param components Component research focus.
 * @param production Production research focus.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class FocusShipResearch(speciesId: SpeciesId,
                 components:ComponentResearchFocus,
                 production:ProductionResearchFocus,
                 instanceId:InstanceId,
                 timestamp:Long)
  extends ShipResearchCommand with StaticTailCommand[ShipResearch] {

  /**
   * The effect of this command.
   */
  val events: List[ShipResearch#Event] =
    List(ShipResearchFocused(speciesId, components, production, instanceId, timestamp))
}
