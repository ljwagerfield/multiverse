package io.multiverse.domain.model.shipResearch

/**
 * Distribution of research efforts across the different areas of a ship.
 * @param engines Percentage of component research spent on engines.
 * @param armour Percentage of component research spent on armour.
 * @param shields Percentage of component research spent on shields.
 * @param colonizingUnits Percentage of component research spent on colonizing units.
 * @param weapons Percentage of component research spent on weapons.
 */
case class ComponentResearchFocus(engines:Int, armour:Int, shields:Int, colonizingUnits:Int, weapons:Int) {
  require(engines + armour + shields + colonizingUnits + weapons == 100)
}
