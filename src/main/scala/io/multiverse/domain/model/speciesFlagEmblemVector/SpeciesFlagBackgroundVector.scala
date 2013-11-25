package io.multiverse.domain.model.speciesFlagEmblemVector

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.{ExplicitAggregateFactory, AggregateRoot}
import io.multiverse.domain.model.common.values.Hash

/**
 * Binary media assets for the emblem of a species flag.
 * @param changes Events pending commitment.
 */
case class SpeciesFlagEmblemVector private(changes: List[SpeciesFlagEmblemVectorEvent])
  extends AggregateRoot[SpeciesFlagEmblemVector, SpeciesFlagEmblemVectorEvent] {

  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: SpeciesFlagEmblemVector = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag emblem vector with event appended and new state applied.
   */
  def apply(event: SpeciesFlagEmblemVectorEvent): SpeciesFlagEmblemVector = unhandledEvent(event)
}

/**
 * Species flag emblem vector factory.
 */
object SpeciesFlagEmblemVector extends ExplicitAggregateFactory[SpeciesFlagEmblemVector, SpeciesFlagEmblemVectorEvent] {
  /**
   * Defines new binary assets for use in the emblems for species flags.
   * @param speciesFlagEmblemVectorId Unique ID for new species assets.
   * @param hash References the binary assets.
   * @param instanceId Instance the event occurred in.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New species assets.
   */
  def define(speciesFlagEmblemVectorId:SpeciesFlagEmblemVectorId, hash:Hash, instanceId:InstanceId, timestamp:Long):SpeciesFlagEmblemVector =
    apply(SpeciesFlagEmblemVectorDefined(speciesFlagEmblemVectorId, hash, instanceId, timestamp))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag emblem vector with event appended and new state applied.
   */
  def apply(event: SpeciesFlagEmblemVectorEvent):SpeciesFlagEmblemVector = {
    event match {
      case event: SpeciesFlagEmblemVectorDefined => SpeciesFlagEmblemVector(Nil :+ event)
      case event: SpeciesFlagEmblemVectorEvent => unhandledEvent(event)
    }
  }
}
