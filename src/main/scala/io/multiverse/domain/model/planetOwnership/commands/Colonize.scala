package io.multiverse.domain.model.planetOwnership.commands

import io.multiverse.domain.model.ship.PlanetColonizationOrdered
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.planetOwnership.{PlanetOwnershipCommand, PlanetColonized, PlanetOwnership}

/**
 * Sets the given species as the new owners of this planet. Any existing species are overthrown.
 * @param planetId Planet being colonized
 * @param canonicalEvent Order which invoked the inbound ship.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class Colonize(planetId: PlanetId, canonicalEvent: PlanetColonizationOrdered, instanceId:InstanceId, timestamp:Long)
  extends PlanetOwnershipCommand with UnconditionalTailCommand[PlanetOwnership] {

  require(canonicalEvent.planetId == planetId, "Canonical event must correspond to the planet represented by this aggregate.")

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: PlanetOwnership#Root): List[PlanetOwnership#Event] =
    List(PlanetColonized(planetId, canonicalEvent, instanceId, timestamp))
}
