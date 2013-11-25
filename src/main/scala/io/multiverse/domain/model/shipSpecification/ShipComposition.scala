package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.shipResearch.{ShieldId, ArmourId, EngineId}

/**
 * Composition of a ship's components.
 * @param engineId Engine to use for ships built with this specification.
 * @param armourId Armour to use for ships built with this specification.
 * @param shieldId Shield to use for ships built with this specification.
 * @param quantifiedWeaponIds Weapons to use for ships built with this specification.
 * @param quantifiedColonizingUnitIds Colonizing units to use for ships built with this specification.
 */
case class ShipComposition(engineId:EngineId,
                           armourId:ArmourId,
                           shieldId:ShieldId,
                           quantifiedWeaponIds:Set[QuantifiedWeaponId],
                           quantifiedColonizingUnitIds:Set[QuantifiedColonizingUnitId])
