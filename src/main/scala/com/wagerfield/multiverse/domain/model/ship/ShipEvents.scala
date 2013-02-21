package com.wagerfield.multiverse.domain.model.ship

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.solarSystem.{StarId, PlanetId}
import com.wagerfield.multiverse.domain.model.shipSpecification.ShipSpecificationId

/**
	* Ship build commissioned: ping event type.
	*/
case class ShipBuildCommissioned(instanceId:InstanceId, planetId:PlanetId, shipSpecificationId:ShipSpecificationId, shipId:ShipId) extends ShipEvent

/**
 * Solar system entry ordered: ship orbits wormhole on arrival.
 */
case class SolarSystemEntryOrdered(instanceId:InstanceId, shipId:ShipId, starId:StarId) extends ShipEvent

/**
 * Planet orbit ordered: aborts if planet becomes hostile.
 */
case class PlanetOrbitOrdered(instanceId:InstanceId, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * Planet colonization ordered: aborts if planet becomes colonised; ship decommissioned after final colonization, if stated in specification.
 */
case class PlanetColonizationOrdered(instanceId:InstanceId, planetId:PlanetId, shipSpecificationId:ShipSpecificationId, shipId:ShipId) extends ShipEvent

/**
 * Planet attack ordered: aborts if planet becomes friendly or vacant.
 */
case class PlanetAttackOrdered(instanceId:InstanceId, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * Ship attack ordered: aborts if ship becomes friendly.
 */
case class ShipAttackOrdered(instanceId:InstanceId, shipId:ShipId, targetShipId:ShipId) extends ShipEvent

/**
 * Ship coordinates ordered.
 */
case class ShipCoordinatesOrdered(instanceId:InstanceId, shipId:ShipId, starId:StarId, xOffset:Int, yOffset:Int) extends ShipEvent

/**
 * Ship order revoked.
 */
case class ShipOrderRevoked(instanceId:InstanceId, shipId:ShipId) extends ShipEvent

/**
 * Inbound ship detected: ping event type; ignore if last journey for ship does not match one provided.
 */
case class InboundShipDetected(instanceId:InstanceId, shipId:ShipId, inboundShipId:ShipId, inboundShipOrder:ShipEvent) extends ShipEvent

/**
 * Ship decommissioned: ships have an incurring cost at a fraction of their price, making decommissioning cost effective in some cases.
 */
case class ShipDecommissioned(instanceId:InstanceId, planetId:PlanetId, shipSpecificationId:ShipSpecificationId, shipId:ShipId) extends ShipEvent