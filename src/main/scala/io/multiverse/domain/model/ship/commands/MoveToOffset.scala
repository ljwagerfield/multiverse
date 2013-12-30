package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.ship.{ShipCoordinatesOrdered, StarOffset, Ship, ShipCommand, ShipId}
import io.multiverse.domain.model.solarSystem.StarId

/**
 * Orders the ship to an offset in a particular solar system.
 * @param shipId Ship under order.
 * @param starId Solar system the offset is relative to.
 * @param offset Coordinates the ship should move to.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class MoveToOffset(shipId:ShipId, starId:StarId, offset:StarOffset, instanceId:InstanceId, timestamp:Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(ShipCoordinatesOrdered(shipId, starId, offset, instanceId, timestamp))
}
