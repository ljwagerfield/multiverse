package io.multiverse.domain.model.shipAssets

import io.multiverse.domain.model.common.{Entity, Aggregate, AggregateRootBase, ExplicitAggregateFactory}

/**
 * Binary media assets for a particular type of ship.
 * @param changes Events pending commitment.
 */
case class ShipAssets private(changes: List[ShipAssetsEvent], id: ShipAssetsId)
  extends AggregateRootBase[ShipAssets, ShipAssetsEvent] with Aggregate[ShipAssets] with Entity[ShipAssetsId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[ShipAssets] = copy(changes = Nil)
}

/**
 * Ship assets factory.
 */
object ShipAssets extends ExplicitAggregateFactory[ShipAssets] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: ShipAssetsDefined): Aggregate[ShipAssets] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[ShipAssetsEvent, Aggregate[ShipAssets]] = {
    case event: ShipAssetsDefined => ShipAssets(Nil :+ event, event.shipAssetsId)
  }
}
