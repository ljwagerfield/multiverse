package io.multiverse.domain.model.system

import io.multiverse.domain.model.common.{AggregateRootBase, Aggregate, ImplicitAggregateFactory, Entity}

/**
 * Entire logical system containing all state and instances.
 * @param changes Events pending commitment.
 * @param id Unique system ID.
 */
case class System private(changes: List[SystemEvent], id: SystemId)
  extends AggregateRootBase[System, SystemEvent] with Aggregate[System] with Entity[SystemId] {

  /**
   * State after committing to the changes.
   */
  lazy val committed: Aggregate[System] = copy(changes = Nil)

  /**
   * Evaluates the event's effects by applying any changes to the new resulting instance.
   */
  override val evaluate: PartialFunction[System#Event, Aggregate[System]] = {
    case event:GamePaused => copy(changes = changes :+ event)
    case event:GameResumed => copy(changes = changes :+ event)
  }
}

/**
 * System factory.
 */
object System extends ImplicitAggregateFactory[System] {

  /**
   * Initializes the system.
   * @param systemId Unique system ID.
   * @return Aggregate representing the initialized system.
   */
  def apply(systemId: SystemId): Aggregate[System] =
    System(Nil, systemId)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent: SystemEvent): Aggregate[System] =
    System(initialEvent.systemId)
}
