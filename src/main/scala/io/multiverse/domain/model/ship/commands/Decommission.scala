package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{ShipDecommissioned, Ship, ShipCommand, ShipId}

/**
 * Orders the ship to self-destruct.
 * @param shipId Ship under order.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class Decommission(shipId:ShipId, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * Event representing the decommissioning of the ship.
   */
  val decommissionedEvent = ShipDecommissioned(shipId, instanceId, timestamp)
  
  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = 
    decommissionedEvent :: DestroyShip(shipId, decommissionedEvent, instanceId, timestamp).events
}
