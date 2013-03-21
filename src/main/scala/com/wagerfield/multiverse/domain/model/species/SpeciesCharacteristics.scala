package com.wagerfield.multiverse.domain.model.species

/**
 * Species attributes which affect ability.
 * @param baseBonus Baseline ability.
 * @param habitatTemperature Required living temperature.
 * @param habitatTemperatureTolerance Range of temperatures the species can tolerate, assuming the habitatTemperature as the ideal midpoint.
 * @param resourceDependencies Required dependencies; species cannot expand without these.
 * @param bonusResourceDependencies Optional dependencies; these provide additional bonuses when acquired.
 */
case class SpeciesCharacteristics(baseBonus:SpeciesBonus,
                                  habitatTemperature:Int,
                                  habitatTemperatureTolerance:Int,
                                  resourceDependencies:List[ResourceDependency],
                                  bonusResourceDependencies:List[BonusResourceDependency])
