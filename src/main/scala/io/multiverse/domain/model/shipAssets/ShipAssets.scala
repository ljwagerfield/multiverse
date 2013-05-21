package io.multiverse.domain.model.shipAssets

import io.multiverse.domain.shared.{Hash, ValidatedEntityAggregateFactory, AggregateRoot}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.shipSpecification.ShipSize

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
  def applyEvent(event: ShipAssetsEvent): ShipAssets = unhandled(event)
}

/**
 * Ship assets factory.
 */
object ShipAssets extends ValidatedEntityAggregateFactory[ShipAssets, ShipAssetsEvent] {
  /**
   * Defines new binary assets for use in ship specifications.
   * @param shipAssetsId Unique ID for new ship assets.
   * @param size Ship size.
   * @param hash References the binary assets.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New ship assets.
   */
  def define(shipAssetsId:ShipAssetsId, size:ShipSize, hash:Hash, instanceId:InstanceId, timeStamp:Long):ShipAssets =
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
