package io.multiverse.domain.model.solarSystem.commands

import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.common.values.ShortAlphabeticName
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.solarSystem.{StarNamed, SolarSystem, SolarSystemCommand, StarId}

/**
 * Assigns a name to the star which is eventually unique within the universe.
 * @param name Unique star name within the universe.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class NameStar(starId: StarId, name:ShortAlphabeticName, instanceId:InstanceId, timestamp:Long)
  extends SolarSystemCommand with StaticTailCommand[SolarSystem] {

  /**
   * The effect of this command.
   */
  val events: List[SolarSystem#Event] =
    List(StarNamed(starId, name, instanceId, timestamp))
}
