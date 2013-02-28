package com.wagerfield.multiverse.domain.model.shipResearch

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.shared.Hash
import com.wagerfield.multiverse.domain.model.species.SpeciesId

/**
 * Ship research focused.
 * @param instanceId Instance the event occurred in.
 * @param speciesId Species who's research has been focused.
 * @param engines Percentage of component research spent on engines.
 * @param armour Percentage of component research spent on armour.
 * @param shields Percentage of component research spent on shields.
 * @param colonizingUnits Percentage of component research spent on colonizing units.
 * @param weapons Percentage of component research spent on weapons.
 * @param economy Percentage of production research spent on economy.
 * @param size Percentage of production research spent on size efficiency.
 * @param effectiveness Percentage of production research spent on effectiveness.
 * @param maintainability Percentage of production research spent on maintainability.
 */
case class ShipResearchFocused(instanceId:InstanceId,
                               speciesId:SpeciesId,
                               engines:Int,
                               armour:Int,
                               shields:Int,
                               colonizingUnits:Int,
                               weapons:Int,
                               economy:Int,
                               size:Int,
                               effectiveness:Int,
                               maintainability:Int) extends ShipResearchEvent
