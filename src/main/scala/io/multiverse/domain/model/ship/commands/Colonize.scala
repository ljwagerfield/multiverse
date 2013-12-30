package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{PlanetColonizationOrdered, Ship, ShipCommand, ShipId}
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Orders the ship to colonize the specified vacant planet.
 * @param shipId Ship under order.
 * @param vacantPlanetId Vacant planet to colonize.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class Colonize(shipId:ShipId, vacantPlanetId:PlanetId, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(PlanetColonizationOrdered(shipId, vacantPlanetId, instanceId, timestamp))
}
