package com.wagerfield.multiverse.domain.model.shipSpecification

/**
 * Ship size ranges 1 to 5 (scout, destroyer, transporter, carrier, leviathan).
 * @param value Ship size value.
 */
case class ShipSize(value:Int) {
  require(value >= 1 && value <= 5, "Ship size must be in the range 1 to 5.")
}
