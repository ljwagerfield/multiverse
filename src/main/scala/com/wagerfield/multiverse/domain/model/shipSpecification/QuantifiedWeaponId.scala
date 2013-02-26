package com.wagerfield.multiverse.domain.model.shipSpecification

import com.wagerfield.multiverse.domain.model.shipResearch.WeaponId

/**
 * A quantity of a particular weapon.
 * @param weaponId The weapon.
 * @param quantity The quantity of that weapon.
 */
case class QuantifiedWeaponId(weaponId:WeaponId, quantity:Int)