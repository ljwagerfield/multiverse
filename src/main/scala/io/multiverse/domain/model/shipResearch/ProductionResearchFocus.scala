package io.multiverse.domain.model.shipResearch

/**
 * Distribution of research efforts across the fields relating to the improvement of production capabilities.
 * @param economy Percentage of production research spent on economy.
 * @param size Percentage of production research spent on size efficiency.
 * @param effectiveness Percentage of production research spent on effectiveness.
 * @param maintainability Percentage of production research spent on maintainability.
 */
case class ProductionResearchFocus(economy:Int, size:Int, effectiveness:Int, maintainability:Int) {
  require(economy + size + effectiveness + maintainability == 100)
}
