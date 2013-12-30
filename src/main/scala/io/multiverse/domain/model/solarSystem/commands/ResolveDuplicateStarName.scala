package io.multiverse.domain.model.solarSystem.commands

import io.multiverse.domain.model.solarSystem.{StarNameDuplicateRenamed, StarId, SolarSystem, SolarSystemCommand}
import io.multiverse.domain.model.common.commands.StaticTailCommand
import io.multiverse.domain.model.common.values.ShortAlphabeticName
import io.multiverse.domain.model.instance.InstanceId

/**
 * Resolves a duplicate star name inconsistency.
 * @param starId Star whose duplicate name is being resolved.
 * @param conflictingStarId Conflicting star which is keeping its name.
 * @param newName New name for this star.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ResolveDuplicateStarName(starId: StarId,
                                    conflictingStarId:StarId,
                                    newName:ShortAlphabeticName,
                                    instanceId:InstanceId, timestamp:Long)
  extends SolarSystemCommand with StaticTailCommand[SolarSystem] {

  /**
   * The effect of this command.
   */
  val events: List[SolarSystem#Event] =
    List(StarNameDuplicateRenamed(starId, conflictingStarId, newName, instanceId, timestamp))
}
