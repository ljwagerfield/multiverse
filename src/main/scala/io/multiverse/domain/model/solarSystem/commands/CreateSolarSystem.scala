package io.multiverse.domain.model.solarSystem.commands

import io.multiverse.domain.model.solarSystem.{SolarSystemCreated, PlanetId, StarId, SolarSystem, SolarSystemCommand}
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.Aggregate

/**
 * Creates a new solar system.
 * @param starId Unique ID for the new star.
 * @param nearStarIds Other stars linked to this one via wormholes.
 * @param planetIds Unique ID for each planet orbiting the star.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class CreateSolarSystem(starId: StarId, nearStarIds: Set[StarId], planetIds: Set[PlanetId], instanceId:InstanceId, timestamp:Long)
  extends SolarSystemCommand with HeadCommand[SolarSystem] {

  require(nearStarIds.size < 5, "Stars must be near to fewer than 5 stars.")
  require(nearStarIds.forall(_ != starId), "Near stars must reference other solar systems.")
  require(planetIds.size > 1 && planetIds.size < 7, "Solar systems must contain between 2 and 6 planets, inclusively.")

  /**
   * The evaluation of this command as a new aggregate.
   */
  lazy val evaluation: Aggregate[SolarSystem] =
    SolarSystem(SolarSystemCreated(starId, nearStarIds, planetIds, instanceId, timestamp))
}
