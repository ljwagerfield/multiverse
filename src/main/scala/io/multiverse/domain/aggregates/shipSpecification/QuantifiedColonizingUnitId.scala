package io.multiverse.domain.aggregates.shipSpecification

import io.multiverse.domain.aggregates.shipResearch.ColonizingUnitId

/**
 * A quantity of a particular colonizing unit.
 * @param colonizingUnitId The colonizing unit.
 * @param quantity The quantity of that unit.
 */
case class QuantifiedColonizingUnitId(colonizingUnitId:ColonizingUnitId, quantity:Int) {
  require(quantity > 0)
}
