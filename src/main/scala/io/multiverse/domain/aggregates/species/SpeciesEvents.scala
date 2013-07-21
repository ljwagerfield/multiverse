package io.multiverse.domain.aggregates.species

import io.multiverse.domain.aggregates.common.ShortAlphabeticName
import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.solarSystem.PlanetId
import io.multiverse.domain.aggregates.speciesAssets.SpeciesAssetsId

/**
 * Species evolved.
 * @param speciesId Unique new species ID.
 * @param name Unique name for the new species.
 * @param flag Flag used by species.
 * @param speciesAssetsId Media assets representing the species.
 * @param planetId Planet where the species originate.
 * @param characteristics Attributes which affect the species ability.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SpeciesEvolved(speciesId: SpeciesId,
                          name: ShortAlphabeticName,
                          flag: SpeciesFlag,
                          speciesAssetsId: SpeciesAssetsId,
                          planetId: PlanetId,
                          characteristics: SpeciesCharacteristics,
                          instanceId: InstanceId,
                          timestamp: Long) extends SpeciesEvent

/**
 * Species name duplicate renamed.
 * @param speciesId The species being renamed.
 * @param conflictingSpeciesId The conflicting species which is keeping their name.
 * @param name The new name of the species.
 * @param instanceId Instance the event occurred in.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class SpeciesNameDuplicateRenamed(speciesId: SpeciesId,
                                       conflictingSpeciesId: SpeciesId,
                                       name: ShortAlphabeticName,
                                       instanceId: InstanceId,
                                       timestamp: Long) extends SpeciesEvent
