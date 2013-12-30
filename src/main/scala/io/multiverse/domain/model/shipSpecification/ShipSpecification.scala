package io.multiverse.domain.model.shipSpecification

import io.multiverse.domain.model.common.{Entity, AggregateRootBase, Aggregate, ExplicitAggregateFactory}

/**
 * Binary media assets for the background of a species flag.
 * @param changes Events pending commitment.
 */
case class ShipSpecification private(changes: List[ShipSpecificationEvent], id: ShipSpecificationId)
  extends AggregateRootBase[ShipSpecification, ShipSpecificationEvent] with Aggregate[ShipSpecification] with Entity[ShipSpecificationId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[ShipSpecification] = copy(changes = Nil)
}

/**
 * Ship specification factory.
 */
object ShipSpecification extends ExplicitAggregateFactory[ShipSpecification] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: ShipSpecified): Aggregate[ShipSpecification] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[ShipSpecification#Event, Aggregate[ShipSpecification]] = {
    case event: ShipSpecified => ShipSpecification(Nil :+ event, event.shipSpecificationId)
  }
}
