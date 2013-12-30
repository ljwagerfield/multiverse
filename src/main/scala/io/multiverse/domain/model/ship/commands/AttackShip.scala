package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.ship.{ShipAttackOrdered, Ship, ShipCommand, ShipId}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.StaticTailCommand

/**
 * Orders the ship to attack the specified non-friendly ship.
 * @param shipId Ship under order.
 * @param nonFriendlyShipId Non-friendly ship to attack.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class AttackShip(shipId: ShipId, nonFriendlyShipId: ShipId, instanceId: InstanceId, timestamp: Long)
  extends ShipCommand with StaticTailCommand[Ship] {

  require(shipId != nonFriendlyShipId, "Attack must be against another ship.")

  /**
   * The effect of this command.
   */
  val events: List[Ship#Event] = List(ShipAttackOrdered(shipId, nonFriendlyShipId, instanceId, timestamp))
}
