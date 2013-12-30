package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{SolarSystemEntryOrdered, Ship, ShipCommand, ShipId}
import io.multiverse.domain.model.solarSystem.StarId

/**
 * Orders the ship to orbit the entry wormhole in a particular solar system.
 * @param shipId Ship under order.
 * @param starId Solar system to enter.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class MoveToEntryWormhole(shipId:ShipId, starId:StarId, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(SolarSystemEntryOrdered(shipId, starId, instanceId, timestamp))
}
