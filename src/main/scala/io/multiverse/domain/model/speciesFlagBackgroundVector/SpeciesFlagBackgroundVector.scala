package io.multiverse.domain.model.speciesFlagBackgroundVector

import io.multiverse.domain.model.common.{Entity, Aggregate, AggregateRootBase, ExplicitAggregateFactory}

/**
 * Binary media assets for the background of a species flag.
 * @param changes Events pending commitment.
 */
case class SpeciesFlagBackgroundVector private(changes: List[SpeciesFlagBackgroundVectorEvent], id: SpeciesFlagBackgroundVectorId)
  extends AggregateRootBase[SpeciesFlagBackgroundVector, SpeciesFlagBackgroundVectorEvent]
  with Aggregate[SpeciesFlagBackgroundVector] with Entity[SpeciesFlagBackgroundVectorId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[SpeciesFlagBackgroundVector] = copy(changes = Nil)
}

/**
 * Species flag background vector factory.
 */
object SpeciesFlagBackgroundVector extends ExplicitAggregateFactory[SpeciesFlagBackgroundVector] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: SpeciesFlagBackgroundVectorDefined): Aggregate[SpeciesFlagBackgroundVector] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[SpeciesFlagBackgroundVector#Event, Aggregate[SpeciesFlagBackgroundVector]] = {
    case event: SpeciesFlagBackgroundVectorDefined => SpeciesFlagBackgroundVector(Nil :+ event, event.speciesFlagBackgroundVectorId)
  }
}
