package com.wagerfield.multiverse.domain.model.ship

import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.solarSystem.{StarId, PlanetId}
import com.wagerfield.multiverse.domain.model.planetIndustry.{ShipBuildCommissioned => ShipBuildCommissionedAtPlanet}

/**
 * Ping event for ship builds.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being built.
 */
case class ShipBuildCommissioned(instanceId:InstanceId, timeStamp:Long, shipId:ShipId, canonicalEvent:ShipBuildCommissionedAtPlanet) extends ShipEvent

/**
 * Solar system entry ordered: ship orbits wormhole on arrival.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being ordered.
 * @param starId Star the ship is bound for.
 */
case class SolarSystemEntryOrdered(instanceId:InstanceId, timeStamp:Long, shipId:ShipId, starId:StarId) extends ShipEvent

/**
 * Planet orbit ordered: aborts if planet becomes hostile.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being ordered.
 * @param planetId Planet the ship is bound for.
 */
case class PlanetOrbitOrdered(instanceId:InstanceId, timeStamp:Long, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * Planet colonization ordered: aborts if planet becomes colonised; ship decommissioned after final colonization if stated in specification.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being ordered.
 * @param planetId Planet the ship has been ordered to colonize.
 */
case class PlanetColonizationOrdered(instanceId:InstanceId, timeStamp:Long, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * Planet attack ordered: aborts if planet becomes friendly or vacant.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being ordered.
 * @param planetId Planet the ship has been ordered to attack.
 */
case class PlanetAttackOrdered(instanceId:InstanceId, timeStamp:Long, shipId:ShipId, planetId:PlanetId) extends ShipEvent

/**
 * * Ship attack ordered: aborts if ship becomes friendly.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being ordered.
 * @param targetShipId Victim ship the attack has been ordered against.
 */
case class ShipAttackOrdered(instanceId:InstanceId, timeStamp:Long, shipId:ShipId, targetShipId:ShipId) extends ShipEvent

/**
 * Ship coordinates ordered.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being ordered.
 * @param starId Star the ship is bound for.
 * @param xOffset The X offset from the star the ship is bound for.
 * @param yOffset The Y offset from the star the ship is bound for.
 */
case class ShipCoordinatesOrdered(instanceId:InstanceId, timeStamp:Long, shipId:ShipId, starId:StarId, xOffset:Int, yOffset:Int) extends ShipEvent

/**
 * Ship ordered to do nothing.
 * @param instanceId Instance the event occurred in.
 * @param shipId Ship being ordered.
 */
case class ShipHaltOrdered(instanceId:InstanceId, timeStamp:Long, shipId:ShipId) extends ShipEvent

/**
 * Ship decommissioned: ships have an incurring cost at a fraction of their price, making decommissioning cost effective in some cases.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param shipId Ship being decommissioned.
 */
case class ShipDecommissioned(instanceId:InstanceId, timeStamp:Long, shipId:ShipId) extends ShipEvent
