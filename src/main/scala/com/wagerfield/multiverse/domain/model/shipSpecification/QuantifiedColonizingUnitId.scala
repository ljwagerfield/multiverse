package com.wagerfield.multiverse.domain.model.shipSpecification

import com.wagerfield.multiverse.domain.model.shipResearch.ColonizingUnitId

/**
 * A quantity of a particular colonizing unit.
 * @param colonizingUnitId The colonizing unit.
 * @param quantity The quantity of that unit.
 */
case class QuantifiedColonizingUnitId(colonizingUnitId:ColonizingUnitId, quantity:Int) {
  require(quantity > 0)
}
