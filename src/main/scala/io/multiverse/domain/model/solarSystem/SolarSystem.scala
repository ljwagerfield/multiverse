package io.multiverse.domain.model.solarSystem

import io.multiverse.domain.model.common.values.ShortAlphanumericName
import io.multiverse.domain.model.common.{Aggregate, AggregateRootBase, Entity, ExplicitAggregateFactory}

/**
 * Solar system composing a star and planets.
 * @param changes Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 * @param namedPlanets Explicitly named planets.
 */
case class SolarSystem private(changes: List[SolarSystemEvent],
                               id: StarId,
                               namedPlanets:Map[ShortAlphanumericName, PlanetId])
  extends AggregateRootBase[SolarSystem, SolarSystemEvent] with Aggregate[SolarSystem] with Entity[StarId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[SolarSystem] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[SolarSystem#Event, Aggregate[SolarSystem]] = {
    case event: StarNamed => copy(changes = changes :+ event)
    case event: PlanetNamed => copy(changes = changes :+ event, namedPlanets = namedPlanets + (event.name -> event.planetId))
    case event: StarNameDuplicateRenamed => copy(changes =  changes :+ event)
  }
}

/**
 * Solar system factory.
 */
object SolarSystem extends ExplicitAggregateFactory[SolarSystem] {

  /**
   * Creates a new instance of the aggregate from the given creation event.
   * @param event Creation event.
   * @return Aggregate.
   */
  def apply(event: SolarSystemCreated): Aggregate[SolarSystem] = evaluate(event)

  /**
   * Evaluates the event's creational effect by outputting a new aggregate instance.
   */
  val evaluate: PartialFunction[SolarSystem#Event, Aggregate[SolarSystem]] = {
    case event: SolarSystemCreated => SolarSystem(Nil :+ event, event.starId, Map())
  }
}
