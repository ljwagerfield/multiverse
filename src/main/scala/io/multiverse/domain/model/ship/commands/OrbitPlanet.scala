package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{PlanetOrbitOrdered, Ship, ShipCommand, ShipId}
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Orders the ship to orbit the specified non-hostile planet.
 * @param shipId Ship under order.
 * @param nonHostilePlanetId Non-hostile planet to orbit.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class OrbitPlanet(shipId:ShipId, nonHostilePlanetId:PlanetId, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(PlanetOrbitOrdered(shipId, nonHostilePlanetId, instanceId, timestamp))
}
