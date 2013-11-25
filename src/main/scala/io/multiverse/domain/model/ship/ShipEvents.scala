package io.multiverse.domain.model.ship

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.solarSystem.{StarId, PlanetId}
import io.multiverse.domain.model.planetIndustry.ShipBuildCommissioned

/**
 * Ship build completed.
 * @param shipId New unique ship ID.
 * @param commissionEvent Event commissioning the completed build.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipBuilt(shipId:ShipId, commissionEvent:ShipBuildCommissioned, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Ship destroyed.
 * @param shipId Destroyed ship.
 * @param destructionEvent Event resulting in the ship's destruction.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipDestroyed(shipId:ShipId, destructionEvent:ShipEvent, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Solar system entry ordered: ship orbits wormhole on arrival.
 * @param shipId Ship being ordered.
 * @param starId Star the ship is bound for.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SolarSystemEntryOrdered(shipId:ShipId, starId:StarId, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Planet orbit ordered: aborts if planet becomes hostile.
 * @param shipId Ship being ordered.
 * @param planetId Planet the ship is bound for.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class PlanetOrbitOrdered(shipId:ShipId, planetId:PlanetId, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Planet colonization ordered: aborts if planet becomes colonised; ship decommissioned after final colonization if stated in specification.
 * @param shipId Ship being ordered.
 * @param planetId Planet the ship has been ordered to colonize.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class PlanetColonizationOrdered(shipId:ShipId, planetId:PlanetId, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Planet attack ordered: aborts if planet becomes friendly or vacant.
 * @param shipId Ship being ordered.
 * @param planetId Planet the ship has been ordered to attack.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class PlanetAttackOrdered(shipId:ShipId, planetId:PlanetId, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * * Ship attack ordered: aborts if ship becomes friendly.
 * @param shipId Ship being ordered.
 * @param targetShipId Victim ship the attack has been ordered against.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipAttackOrdered(shipId:ShipId, targetShipId:ShipId, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Ship coordinates ordered.
 * @param shipId Ship being ordered.
 * @param starId Star the ship is bound for.
 * @param offset The offset from the star the ship is bound for.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipCoordinatesOrdered(shipId:ShipId, starId:StarId, offset:StarOffset, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Ship ordered to do nothing.
 * @param shipId Ship being ordered.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipHaltOrdered(shipId:ShipId, instanceId:InstanceId, timestamp:Long) extends ShipEvent

/**
 * Ship decommissioned: ships have an incurring cost at a fraction of their price, making decommissioning cost effective in some cases.
 * @param shipId Ship being decommissioned.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ShipDecommissioned(shipId:ShipId, instanceId:InstanceId, timestamp:Long) extends ShipEvent
