package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{ShipHaltOrdered, Ship, ShipCommand, ShipId}

/**
 * Orders the ship to do nothing.
 * @param shipId Ship under order.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class HaltShip(shipId:ShipId, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(ShipHaltOrdered(shipId, instanceId, timestamp))
}
