package io.multiverse.domain.model.speciesAssets

import io.multiverse.domain.model.common.{Entity, Aggregate, AggregateRootBase, ExplicitAggregateFactory}

/**
 * Binary media assets for a particular type of species.
 * @param changes Events pending commitment.
 */
case class SpeciesAssets private(changes: List[SpeciesAssetsEvent], id: SpeciesAssetsId)
  extends AggregateRootBase[SpeciesAssets, SpeciesAssetsEvent] with Aggregate[SpeciesAssets] with Entity[SpeciesAssetsId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[SpeciesAssets] = copy(changes = Nil)
}

/**
 * Species assets factory.
 */
object SpeciesAssets extends ExplicitAggregateFactory[SpeciesAssets] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: SpeciesAssetsDefined): Aggregate[SpeciesAssets] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[SpeciesAssets#Event, Aggregate[SpeciesAssets]] = {
    case event: SpeciesAssetsDefined => SpeciesAssets(Nil :+ event, event.speciesAssetsId)
  }
}
