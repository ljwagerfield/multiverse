package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.shipResearch.WeaponId

/**
 * A quantity of a particular weapon.
 * @param weaponId The weapon.
 * @param quantity The quantity of that weapon.
 */
case class QuantifiedWeaponId(weaponId:WeaponId, quantity:Int) {
  require(quantity > 0)
}
