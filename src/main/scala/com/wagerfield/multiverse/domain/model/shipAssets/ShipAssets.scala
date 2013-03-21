package com.wagerfield.multiverse.domain.model.shipAssets

import com.wagerfield.multiverse.domain.shared.{Hash, ValidatedEntityAggregateFactory, AggregateRoot}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Binary media assets for a particular type of ship.
 * @param uncommittedEvents Events pending commitment.
 */
case class ShipAssets private(uncommittedEvents: List[ShipAssetsEvent])
  extends AggregateRoot[ShipAssets, ShipAssetsEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: ShipAssets = copy(uncommittedEvents = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Ship assets with event appended and new state applied.
   */
  def applyEvent(event: ShipAssetsEvent): ShipAssets = unhandled(event)
}

/**
 * Ship assets factory.
 */
object ShipAssets extends ValidatedEntityAggregateFactory[ShipAssets, ShipAssetsEvent] {
  /**
   * Defines new binary assets for use in ship specifications.
   * @param shipAssetsId Unique ID for new ship assets.
   * @param size Ship size ranges 1 to 5 (scout, destroyer, transporter, carrier, leviathan).
   * @param hash References the binary assets.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship assets.
   */
  def define(shipAssetsId:ShipAssetsId, size:Int, hash:Hash, instanceId:InstanceId, timeStamp:Long):ShipAssets =
    applyEvent(ShipAssetsDefined(instanceId, timeStamp, shipAssetsId, size, hash))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Ship assets with event appended and new state applied.
   */
  def applyEvent(event: ShipAssetsEvent):ShipAssets = {
    event match {
      case event: ShipAssetsDefined => ShipAssets(Nil :+ event)
      case event: ShipAssetsEvent => unhandled(event)
    }
  }
}
