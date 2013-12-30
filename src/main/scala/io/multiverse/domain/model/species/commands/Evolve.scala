package io.multiverse.domain.model.species.commands

import io.multiverse.domain.model.species.{SpeciesEvolved, Species, SpeciesCommand, SpeciesCharacteristics, SpeciesFlag, SpeciesId}
import io.multiverse.domain.model.common.values.ShortAlphabeticName
import io.multiverse.domain.model.speciesAssets.SpeciesAssetsId
import io.multiverse.domain.model.solarSystem.PlanetId
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.HeadCommand
import io.multiverse.domain.model.common.Aggregate

/**
 * Evolves a new species.
 * @param speciesId Unique new species ID.
 * @param name Unique name for the new species.
 * @param flag Flag used by species.
 * @param speciesAssetsId Media assets representing the species.
 * @param planetId Planet where the species originate.
 * @param characteristics Attributes which affect the species ability.
 * @param instanceId Instance invoking the command.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class Evolve(speciesId:SpeciesId,
                  name:ShortAlphabeticName,
                  flag:SpeciesFlag,
                  speciesAssetsId:SpeciesAssetsId,
                  planetId:PlanetId,
                  characteristics:SpeciesCharacteristics,
                  instanceId:InstanceId,
                  timestamp:Long)
  extends SpeciesCommand with HeadCommand[Species] {

  /**
   * The evaluation of this command as a new aggregate.
   */
  val evaluation: Aggregate[Species] = Species(SpeciesEvolved(
    speciesId,
    name,
    flag,
    speciesAssetsId,
    planetId,
    characteristics,
    instanceId,
    timestamp))
}
