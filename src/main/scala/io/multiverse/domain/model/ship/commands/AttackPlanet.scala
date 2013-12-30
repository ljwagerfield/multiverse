package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{PlanetAttackOrdered, Ship, ShipCommand, ShipId}
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Orders the ship to attack the specified non-friendly planet.
 * @param shipId Ship under order.
 * @param nonFriendlyPlanetId Non-friendly planet to attack
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class AttackPlanet(shipId:ShipId, nonFriendlyPlanetId:PlanetId, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(PlanetAttackOrdered(shipId, nonFriendlyPlanetId, instanceId, timestamp))
}
