package io.multiverse.domain.aggregates.ship

/**
 * Solar system coordinates represented as a relative offset from its star (i.e. the center of the solar systen).
 */
case class StarOffset(xOffset:Int, yOffset:Int) {
  require(xOffset >= 0-StarOffset.maxAbsCoordinate && xOffset <= StarOffset.maxAbsCoordinate, "Star offsets must be within range.")
  require(yOffset >= 0-StarOffset.maxAbsCoordinate && yOffset <= StarOffset.maxAbsCoordinate, "Star offsets must be within range.")
}

/**
 * Star offset constants.
 */
object StarOffset {
  /**
   * Maximum absolute value of a solar system coordinate relative to its star.
   */
  val maxAbsCoordinate = 1000
}
