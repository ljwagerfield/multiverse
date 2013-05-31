package io.multiverse.domain.aggregates.speciesFlagBackgroundVector

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.common.{Hash, ExplicitAggregateFactory, AggregateRoot}

/**
 * Binary media assets for the background of a species flag.
 * @param changes Events pending commitment.
 */
case class SpeciesFlagBackgroundVector private(changes: List[SpeciesFlagBackgroundVectorEvent])
  extends AggregateRoot[SpeciesFlagBackgroundVector, SpeciesFlagBackgroundVectorEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: SpeciesFlagBackgroundVector = copy(changes = Nil)

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag background vector with event appended and new state applied.
   */
  def applyEvent(event: SpeciesFlagBackgroundVectorEvent): SpeciesFlagBackgroundVector = unhandled(event)
}

/**
 * Species flag background vector factory.
 */
object SpeciesFlagBackgroundVector extends ExplicitAggregateFactory[SpeciesFlagBackgroundVector, SpeciesFlagBackgroundVectorEvent] {
  /**
   * Defines new binary assets for use in the backgrounds for species flags.
   * @param speciesFlagBackgroundVectorId Unique ID for new species assets.
   * @param hash References the binary assets.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return New species assets.
   */
  def define(speciesFlagBackgroundVectorId:SpeciesFlagBackgroundVectorId, hash:Hash, instanceId:InstanceId, timeStamp:Long):SpeciesFlagBackgroundVector =
    applyEvent(SpeciesFlagBackgroundVectorDefined(instanceId, timeStamp, speciesFlagBackgroundVectorId, hash))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species flag background vector with event appended and new state applied.
   */
  def applyEvent(event: SpeciesFlagBackgroundVectorEvent):SpeciesFlagBackgroundVector = {
    event match {
      case event: SpeciesFlagBackgroundVectorDefined => SpeciesFlagBackgroundVector(Nil :+ event)
      case event: SpeciesFlagBackgroundVectorEvent => unhandled(event)
    }
  }
}
