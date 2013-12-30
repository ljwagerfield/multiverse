package io.multiverse.domain.model.planetOwnership.commands

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.planetOwnership.{PlanetAbandoned, PlanetOwnership, PlanetOwnershipCommand}
import io.multiverse.domain.model.solarSystem.PlanetId

/**
 * Abandons the current inhabitants from the planet.
 * @param planetId Planet to abandon.
 * @param instanceId Instance invoking this command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class Abandon(planetId: PlanetId, instanceId:InstanceId, timestamp:Long)
  extends PlanetOwnershipCommand with UnconditionalTailCommand[PlanetOwnership] {

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: PlanetOwnership#Root): List[PlanetOwnership#Event] =
    if (aggregateRoot.isVacant)
      Nil
    else
      List(PlanetAbandoned(planetId, instanceId, timestamp))
}
