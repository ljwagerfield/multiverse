package io.multiverse.domain.model.solarSystem.commands

import io.multiverse.domain.model.solarSystem.{PlanetNamed, SolarSystem, SolarSystemCommand, PlanetId, StarId}
import io.multiverse.domain.model.common.values.ShortAlphanumericName
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.{CommandPrerequisite, TailCommand}

/**
 * Assigns a name to the planet which is unique within the solar system.
 * @param planetId Planet to name.
 * @param name Unique name within the solar system.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class NamePlanet(starId: StarId, planetId:PlanetId, name:ShortAlphanumericName, instanceId:InstanceId, timestamp:Long)
  extends SolarSystemCommand with TailCommand[SolarSystem] {

  /**
   * Predicates that must pass for the command to be successfully evaluated.
   */
  val prerequisites: List[CommandPrerequisite[SolarSystem#Root]] =
    List(NamePlanet.UniquePlanetName(this))

  /**
   * Produces the sequence of events caused by the successful invocation of this command.
   * @param aggregateRoot Aggregate to invoke command against.
   * @return Event sequence.
   */
  protected def eventsFor(aggregateRoot: SolarSystem#Root): List[SolarSystem#Event] =
    List(PlanetNamed(starId, planetId, name, instanceId, timestamp))
}

/**
 * Command prerequisites for [[io.multiverse.domain.model.solarSystem.commands.NamePlanet]].
 */
object NamePlanet {

  /**
   * Planet name must be unique within this solar system.
   * @param command Command to validate.
   */
  case class UniquePlanetName(command: NamePlanet) extends CommandPrerequisite[SolarSystem] {

    /**
     * Tests the validity of a specific command rule given the context of the provided aggregate.
     * @param aggregateRoot Aggregate the command is being tested against.
     * @return True if the rule passes, otherwise false.
     */
    def isValid(aggregateRoot: SolarSystem): Boolean =
      aggregateRoot.namedPlanets.get(command.name).forall(_ == command.planetId)
  }
}
