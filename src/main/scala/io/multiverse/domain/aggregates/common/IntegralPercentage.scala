package io.multiverse.domain.aggregates.common

/**
 * Percentage represented as an integer with the range 0 to 100.
 * @param value
 */
case class IntegralPercentage(value:Int) {
  require(value >= 0 && value <= 100)
}
