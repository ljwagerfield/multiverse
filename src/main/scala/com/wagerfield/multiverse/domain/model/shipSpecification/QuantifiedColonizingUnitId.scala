package com.wagerfield.multiverse.domain.model.shipSpecification

import com.wagerfield.multiverse.domain.model.shipResearch.ColonizingUnitId

/**
 * A given quantity of a particular colonizing unit.
 */
case class QuantifiedColonizingUnitId(colonizingUnitId:ColonizingUnitId, quantity:Int)