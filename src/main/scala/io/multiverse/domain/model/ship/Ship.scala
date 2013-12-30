package io.multiverse.domain.model.ship

import io.multiverse.domain.model.common.{Aggregate, AggregateRootBase, ExplicitAggregateFactory, Entity}

/**
 * Ship.
 * @param changes Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 */
case class Ship private(changes: List[ShipEvent], id: ShipId)
  extends AggregateRootBase[Ship, ShipEvent] with Aggregate[Ship] with Entity[ShipId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[Ship] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[Ship#Event, Aggregate[Ship]] = {
    case event: PlanetAttackOrdered => copy(changes = changes :+ event)
    case event: PlanetColonizationOrdered => copy(changes = changes :+ event)
    case event: PlanetOrbitOrdered => copy(changes = changes :+ event)
    case event: ShipAttackOrdered => copy(changes = changes :+ event)
    case event: ShipBuilt => copy(changes = changes :+ event)
    case event: ShipCoordinatesOrdered => copy(changes = changes :+ event)
    case event: ShipDecommissioned => copy(changes = changes :+ event)
    case event: ShipDestroyed => copy(changes = changes :+ event)
    case event: ShipHaltOrdered => copy(changes = changes :+ event)
    case event: SolarSystemEntryOrdered => copy(changes = changes :+ event)
  }
}

/**
 * Ship factory.
 */
object Ship extends ExplicitAggregateFactory[Ship] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: ShipBuilt): Aggregate[Ship] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[Ship#Event, Aggregate[Ship]] = {
    case event: ShipBuilt => Ship(Nil :+ event, event.shipId)
  }
}
