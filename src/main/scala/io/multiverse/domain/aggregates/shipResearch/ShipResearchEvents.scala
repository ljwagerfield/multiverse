package io.multiverse.domain.aggregates.shipResearch

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.species.SpeciesId

/**
 * Ship research focused.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param speciesId Species who's research has been focused.
 * @param components Component research focus.
 * @param production Production research focus.
 */
case class ShipResearchFocused(instanceId:InstanceId,
                               timeStamp:Long,
                               speciesId:SpeciesId,
                               components:ComponentResearchFocus,
                               production:ProductionResearchFocus) extends ShipResearchEvent
