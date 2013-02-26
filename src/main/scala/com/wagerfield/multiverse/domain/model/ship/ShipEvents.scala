package com.wagerfield.multiverse.domain.model.ship

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.solarSystem.{StarId, PlanetId}
import com.wagerfield.multiverse.domain.model.shipSpecification.ShipSpecificationId

/**
 * Ship build commissioned: ping event type.
 * @param instanceId Instance the event occurred in.
 * @param planetId Planet where the ship build was commissioned.
 * @param shipSpecificationId Specification the new ship will be based on.
 * @param shipId Unique ID for the new ship.
 */
case class ShipBuildCommissioned(instanceId:InstanceId, planetId:PlanetId, shipSpecificationId:ShipSpecificationId, shipId:ShipId) extends ShipEvent

/**
 * Solar system entry ordered: ship orbits wormhole on arrival.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being ordered.
 * @param starId The star the ship is bound for.
 */
case class SolarSystemEntryOrdered(instanceId:InstanceId, shipId:ShipId, starId:StarId) extends ShipEvent

/**
 * Planet orbit ordered: aborts if planet becomes hostile.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being ordered.
 * @param planetId The planet the ship is bound for.
 */
case class PlanetOrbitOrdered(instanceId:InstanceId, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * Planet colonization ordered: aborts if planet becomes colonised; ship decommissioned after final colonization if stated in specification.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being ordered.
 * @param planetId Planet the ship has been ordered to colonize.
 */
case class PlanetColonizationOrdered(instanceId:InstanceId, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * Planet attack ordered: aborts if planet becomes friendly or vacant.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being ordered.
 * @param planetId Planet the ship has been ordered to attack.
 */
case class PlanetAttackOrdered(instanceId:InstanceId, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * * Ship attack ordered: aborts if ship becomes friendly.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being ordered.
 * @param targetShipId Victim ship the attack has been ordered against.
 */
case class ShipAttackOrdered(instanceId:InstanceId, shipId:ShipId, targetShipId:ShipId) extends ShipEvent

/**
 * Ship coordinates ordered.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being ordered.
 * @param starId The star the ship is bound for.
 * @param xOffset The X offset from the star the ship is bound for.
 * @param yOffset The Y offset from the star the ship is bound for.
 */
case class ShipCoordinatesOrdered(instanceId:InstanceId, shipId:ShipId, starId:StarId, xOffset:Int, yOffset:Int) extends ShipEvent

/**
 * Ship order revoked.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being ordered.
 */
case class ShipOrderRevoked(instanceId:InstanceId, shipId:ShipId) extends ShipEvent

/**
 * Inbound ship detected: ping event type; ignore if last order for ship does not match one provided.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship which detected an inbound ship.
 * @param inboundShipId The inbound ship.
 * @param inboundShipOrder The order which invoked the inbound ship.
 */
case class InboundShipDetected(instanceId:InstanceId, shipId:ShipId, inboundShipId:ShipId, inboundShipOrder:ShipEvent) extends ShipEvent

/**
 * Ship decommissioned: ships have an incurring cost at a fraction of their price, making decommissioning cost effective in some cases.
 * @param instanceId Instance the event occurred in.
 * @param shipId The ship being decommissioned.
 */
case class ShipDecommissioned(instanceId:InstanceId, shipId:ShipId) extends ShipEvent