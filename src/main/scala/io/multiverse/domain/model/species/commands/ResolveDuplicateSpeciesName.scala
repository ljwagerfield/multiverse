package io.multiverse.domain.model.species.commands

import io.multiverse.domain.model.species.{SpeciesNameDuplicateRenamed, Species, SpeciesCommand, SpeciesId}
import io.multiverse.domain.model.common.values.ShortAlphabeticName
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.StaticTailCommand

/**
 * Resolves a duplicate species name inconsistency.
 * @param speciesId Species whose duplicate name is being resolved.
 * @param conflictingSpeciesId Conflicting species which is keeping its name.
 * @param newName New name for this species.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class ResolveDuplicateSpeciesName(speciesId: SpeciesId,
                                       conflictingSpeciesId: SpeciesId,
                                       newName: ShortAlphabeticName,
                                       instanceId: InstanceId,
                                       timestamp: Long)
  extends SpeciesCommand with StaticTailCommand[Species] {

  /**
   * The effect of this command.
   */
  val events: List[Species#Event] =
    List(SpeciesNameDuplicateRenamed(speciesId, conflictingSpeciesId, newName, instanceId, timestamp))
}
