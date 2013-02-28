package com.wagerfield.multiverse.domain.model.species

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.solarSystem.{SolarSystemEvent, StarId, PlanetId}
import com.wagerfield.multiverse.domain.model.speciesAssets.SpeciesAssetsId

/**
 * Species evolved.
 * @param instanceId Instance the event occurred in.
 * @param speciesId The evolved species.
 * @param name The name of the species.
 * @param flag The species' flag.
 * @param speciesAssetsId The binary media assets for the species.
 * @param planetId The planet where the species originated.
 * @param baseBonus The base bonus applied.
 * @param habitatTemperature The ideal habitat temperature for the species.
 * @param habitatTemperatureTolerance The delta from either side of the ideal habitat temperature which the species can tolerate.
 * @param resourceDependencies The required dependencies; species cannot expand without these.
 * @param bonusResourceDependencies The optional dependencies; these provide additional bonuses when acquired.
 */
case class SpeciesEvolved(instanceId:InstanceId,
                          speciesId:SpeciesId,
                          name:String,
                          flag:SpeciesFlag,
                          speciesAssetsId:SpeciesAssetsId,
                          planetId:PlanetId,
                          baseBonus:SpeciesBonus,
                          habitatTemperature:Int,
                          habitatTemperatureTolerance:Int,
                          resourceDependencies:List[ResourceDependency],
                          bonusResourceDependencies:List[BonusResourceDependency]) extends SpeciesEvent

/**
 * Species name duplicate renamed.
 * @param instanceId Instance the event occurred in.
 * @param speciesId The species being renamed.
 * @param conflictingSpeciesId The conflicting species which is keeping their name.
 * @param name The new name of the species.
 */
case class SpeciesNameDuplicateRenamed(instanceId:InstanceId, speciesId: SpeciesId, conflictingSpeciesId: SpeciesId, name: String) extends SpeciesEvent
