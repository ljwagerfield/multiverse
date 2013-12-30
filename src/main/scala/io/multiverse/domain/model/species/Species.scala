package io.multiverse.domain.model.species

import io.multiverse.domain.model.common.{Aggregate, AggregateRootBase, ExplicitAggregateFactory, Entity}

/**
 * Solar system composing a star and planets.
 * @param changes Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 */
case class Species private(changes: List[SpeciesEvent], id: SpeciesId)
  extends AggregateRootBase[Species, SpeciesEvent] with Aggregate[Species] with Entity[SpeciesId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[Species] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[Species#Event, Aggregate[Species]] = {
    case event: SpeciesNameDuplicateRenamed => copy(changes = changes :+ event)
  }
}

/**
 * Solar system factory.
 */
object Species extends ExplicitAggregateFactory[Species] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: SpeciesEvolved): Aggregate[Species] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[Species#Event, Aggregate[Species]] = {
    case event: SpeciesEvolved => Species(Nil :+ event, event.speciesId)
  }
}
