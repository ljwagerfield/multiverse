package io.multiverse.domain.aggregates.speciesAssets

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.{Hash, ExplicitAggregateFactory, AggregateRoot}

/**
 * Binary media assets for a particular type of species.
 * @param changes Events pending commitment.
 */
case class SpeciesAssets private(changes: List[SpeciesAssetsEvent])
  extends AggregateRoot[SpeciesAssets, SpeciesAssetsEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: SpeciesAssets = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species assets with event appended and new state applied.
   */
  def apply(event: SpeciesAssetsEvent): SpeciesAssets = unhandledEvent(event)
}

/**
 * Species assets factory.
 */
object SpeciesAssets extends ExplicitAggregateFactory[SpeciesAssets, SpeciesAssetsEvent] {
  /**
   * Defines new binary assets for use in new species.
   * @param speciesAssetsId Unique ID for new species assets.
   * @param hash References the binary assets.
   * @param instanceId Instance the event occurred in.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New species assets.
   */
  def define(speciesAssetsId:SpeciesAssetsId, hash:Hash, instanceId:InstanceId, timestamp:Long):SpeciesAssets =
    apply(SpeciesAssetsDefined(speciesAssetsId, hash, instanceId, timestamp))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species assets with event appended and new state applied.
   */
  def apply(event: SpeciesAssetsEvent):SpeciesAssets = {
    event match {
      case event: SpeciesAssetsDefined => SpeciesAssets(Nil :+ event)
      case event: SpeciesAssetsEvent => unhandledEvent(event)
    }
  }
}
