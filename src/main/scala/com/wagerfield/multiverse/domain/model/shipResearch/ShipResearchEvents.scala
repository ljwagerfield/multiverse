package com.wagerfield.multiverse.domain.model.shipResearch

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash
import com.wagerfield.multiverse.domain.model.species.SpeciesId

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
