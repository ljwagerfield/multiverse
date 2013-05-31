package io.multiverse.domain.aggregates.species

import io.multiverse.domain.aggregates.common.ShortAlphabeticName
import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.solarSystem.PlanetId
import io.multiverse.domain.aggregates.speciesAssets.SpeciesAssetsId

/**
 * Species evolved.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param speciesId Unique new species ID.
 * @param name Unique name for the new species.
 * @param flag Flag used by species.
 * @param speciesAssetsId Media assets representing the species.
 * @param planetId Planet where the species originate.
 * @param characteristics Attributes which affect the species ability.
 */
case class SpeciesEvolved(instanceId:InstanceId,
                          timeStamp:Long,
                          speciesId:SpeciesId,
                          name:ShortAlphabeticName,
                          flag:SpeciesFlag,
                          speciesAssetsId:SpeciesAssetsId,
                          planetId:PlanetId,
                          characteristics:SpeciesCharacteristics) extends SpeciesEvent

/**
 * Species name duplicate renamed.
 * @param instanceId Instance the event occurred in.
 * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 * @param speciesId The species being renamed.
 * @param conflictingSpeciesId The conflicting species which is keeping their name.
 * @param name The new name of the species.
 */
case class SpeciesNameDuplicateRenamed(instanceId:InstanceId,
                                       timeStamp:Long,
                                       speciesId: SpeciesId,
                                       conflictingSpeciesId: SpeciesId,
                                       name:ShortAlphabeticName) extends SpeciesEvent
