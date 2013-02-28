package com.wagerfield.multiverse.domain.model.shipSpecification

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.species.SpeciesId
import com.wagerfield.multiverse.domain.model.shipAssets.ShipAssetsId
import com.wagerfield.multiverse.domain.model.shipResearch.{WeaponId, ShieldId, ArmourId, EngineId}

/**
 * Ship specified.
 * @param instanceId Instance the event occurred in.
 * @param shipSpecificationId ID for the new specification.
 * @param authorId The species who authored the specification.
 * @param shipAssetsId Binary assets associated with the ship specification.
 * @param size Size of the ship this specification defines.
 * @param name The specification name.
 * @param engineId The engine to use for ships built with this specification.
 * @param armourId The armour to use for ships built with this specification.
 * @param shieldId The shield to use for ships built with this specification.
 * @param quantifiedWeaponIds The weapons to use for ships built with this specification.
 * @param quantifiedColonizingUnitIds The colonizing units to use for ships built with this specification.
 */
case class ShipSpecified(instanceId:InstanceId,
                         shipSpecificationId:ShipSpecificationId,
                         authorId:SpeciesId,
                         shipAssetsId:ShipAssetsId,
                         size:Int,
                         name:String,
                         engineId:EngineId,
                         armourId:ArmourId,
                         shieldId:ShieldId,
                         quantifiedWeaponIds:Set[QuantifiedWeaponId],
                         quantifiedColonizingUnitIds:Set[QuantifiedColonizingUnitId]) extends ShipSpecificationEvent
