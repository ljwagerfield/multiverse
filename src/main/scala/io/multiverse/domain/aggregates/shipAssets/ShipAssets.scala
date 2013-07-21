package io.multiverse.domain.aggregates.shipAssets

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.shipSpecification.ShipSize
import io.multiverse.domain.aggregates.common.{Hash, ExplicitAggregateFactory, AggregateRoot}

/**
 * Binary media assets for a particular type of ship.
 * @param changes Events pending commitment.
 */
case class ShipAssets private(changes: List[ShipAssetsEvent])
  extends AggregateRoot[ShipAssets, ShipAssetsEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: ShipAssets = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Ship assets with event appended and new state applied.
   */
  def apply(event: ShipAssetsEvent): ShipAssets = unhandledEvent(event)
}

/**
 * Ship assets factory.
 */
object ShipAssets extends ExplicitAggregateFactory[ShipAssets, ShipAssetsEvent] {
  /**
   * Defines new binary assets for use in ship specifications.
   * @param shipAssetsId Unique ID for new ship assets.
   * @param size Ship size.
   * @param hash References the binary assets.
   * @param instanceId Instance the event occurred in.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship assets.
   */
  def define(shipAssetsId:ShipAssetsId, size:ShipSize, hash:Hash, instanceId:InstanceId, timestamp:Long):ShipAssets =
    apply(ShipAssetsDefined(shipAssetsId, size, hash, instanceId, timestamp))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Ship assets with event appended and new state applied.
   */
  def apply(event: ShipAssetsEvent):ShipAssets = {
    event match {
      case event: ShipAssetsDefined => ShipAssets(Nil :+ event)
      case event: ShipAssetsEvent => unhandledEvent(event)
    }
  }
}
