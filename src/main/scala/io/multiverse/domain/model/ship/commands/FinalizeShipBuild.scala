package io.multiverse.domain.model.ship.commands

import io.multiverse.domain.model.common.Aggregate
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.planetIndustry.ShipBuildCommissioned
import io.multiverse.domain.model.ship.{ShipBuilt, Ship, ShipCommand, ShipId}

/**
 * Orders the ship to do nothing.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class FinalizeShipBuild(canonicalEvent: ShipBuildCommissioned, instanceId: InstanceId, timestamp: Long)
  extends ShipCommand with HeadCommand[Ship] {

  /**
   * Ship that has been built.
   */
  val shipId: ShipId = canonicalEvent.shipId

  /**
   * The evaluation of this command as a new aggregate.
   */
  val evaluation: Aggregate[Ship] = Ship(ShipBuilt(shipId, canonicalEvent, instanceId, timestamp))
}
