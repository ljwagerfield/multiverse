package io.multiverse.domain.model.speciesFlagEmblemVector

import io.multiverse.domain.model.common.{Entity, Aggregate, AggregateRootBase, ExplicitAggregateFactory}

/**
 * Binary media assets for the emblem of a species flag.
 * @param changes Events pending commitment.
 */
case class SpeciesFlagEmblemVector private(changes: List[SpeciesFlagEmblemVectorEvent], id: SpeciesFlagEmblemVectorId)
  extends AggregateRootBase[SpeciesFlagEmblemVector, SpeciesFlagEmblemVectorEvent]
  with Aggregate[SpeciesFlagEmblemVector] with Entity[SpeciesFlagEmblemVectorId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[SpeciesFlagEmblemVector] = copy(changes = Nil)
}

/**
 * Species flag emblem vector factory.
 */
object SpeciesFlagEmblemVector extends ExplicitAggregateFactory[SpeciesFlagEmblemVector] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: SpeciesFlagEmblemVectorDefined): Aggregate[SpeciesFlagEmblemVector] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[SpeciesFlagEmblemVector#Event, Aggregate[SpeciesFlagEmblemVector]] = {
    case event: SpeciesFlagEmblemVectorDefined => SpeciesFlagEmblemVector(Nil :+ event, event.speciesFlagEmblemVectorId)
  }
}
