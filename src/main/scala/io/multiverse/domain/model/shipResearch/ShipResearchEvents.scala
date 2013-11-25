package io.multiverse.domain.model.shipResearch

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.species.SpeciesId

/**
 * Ship research focused.
 * @param speciesId Species who's research has been focused.
 * @param components Component research focus.
 * @param production Production research focus.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipResearchFocused(speciesId:SpeciesId,
                               components:ComponentResearchFocus,
                               production:ProductionResearchFocus,
                               instanceId:InstanceId,
                               timestamp:Long) extends ShipResearchEvent
