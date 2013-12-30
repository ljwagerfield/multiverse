package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{ShipDestroyed, DestructiveShipEvent, Ship, ShipCommand, ShipId}

/**
 * Immediately ends the ship's existence.
 * @param shipId Ship being destroyed.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class DestroyShip(shipId:ShipId, destructionEvent: DestructiveShipEvent, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(ShipDestroyed(shipId, destructionEvent, instanceId, timestamp))
}
