package com.wagerfield.multiverse.domain.model.shipSpecification

import com.wagerfield.multiverse.domain.model.shipResearch.WeaponId

/**
 * A given quantity of a particular weapon.
 */
case class QuantifiedWeaponId(weaponId:WeaponId, quantity:Int)