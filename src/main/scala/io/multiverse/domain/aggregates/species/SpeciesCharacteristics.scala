package io.multiverse.domain.aggregates.species

/**
 * Species attributes which affect ability.
 * @param baseBonus Baseline ability.
 * @param habitatTemperature Required living temperature in degrees celsius.
 * @param habitatTemperatureTolerance Range of temperatures the species thrives in, assuming the habitatTemperature as ideal midpoint.
 *                                    All temperatures within this range are equally ideal to the species.
 * @param resourceDependencies Required dependencies; species cannot expand without these.
 * @param bonusResourceDependencies Optional dependencies; these provide additional bonuses when acquired.
 */
case class SpeciesCharacteristics(baseBonus:SpeciesBonus,
                                  habitatTemperature:Int,
                                  habitatTemperatureTolerance:Int,
                                  resourceDependencies:List[ResourceDependency],
                                  bonusResourceDependencies:List[BonusResourceDependency]) {

  require(habitatTemperature >= 0-SpeciesCharacteristics.maxAbsTemperature &&
    habitatTemperature <= SpeciesCharacteristics.maxAbsTemperature,
    "Temperatures must be within range.")

  require(habitatTemperatureTolerance >= SpeciesCharacteristics.minTolerance &&
    habitatTemperatureTolerance <= SpeciesCharacteristics.maxTolerance,
    "Temperature tolerance must within range.")
}

object SpeciesCharacteristics {
  /**
   * Maximum absolute value of a solar system coordinate relative to its star.
   */
  val maxAbsTemperature = 100

  /**
   * Minimum range of temperatures the species can tolerate.
   */
  val minTolerance = 10

  /**
   * Maximum range of temperatures the species can tolerate.
   */
  val maxTolerance = maxAbsTemperature * 2

}
