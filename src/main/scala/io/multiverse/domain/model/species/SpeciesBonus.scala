package io.multiverse.domain.model.species

/**
 * Species bonus.
 * @param researchPoints Increases research productivity.
 * @param weaponAccuracyPoints Increases weapon accuracy in battles.
 */
case class SpeciesBonus(researchPoints:Int, weaponAccuracyPoints:Int) {
  require(researchPoints >= 0, "Points must be positive.")
  require(weaponAccuracyPoints >= 0, "Points must be positive.")
}
