package com.wagerfield.multiverse.domain.model.species

import com.wagerfield.multiverse.domain.shared.{AggregateRoot, ValidatedEntityAggregateFactory, Entity}
import com.wagerfield.multiverse.domain.model.instance.InstanceId
import com.wagerfield.multiverse.domain.model.speciesAssets.SpeciesAssetsId
import com.wagerfield.multiverse.domain.model.solarSystem.PlanetId

/**
 * Solar system composing a star and planets.
 * @param uncommittedEvents Events pending commitment.
 * @param id Unique star ID. Solar systems are uniquely identified by their stars.
 */
case class Species private(uncommittedEvents: List[SpeciesEvent], id: SpeciesId)
  extends Entity[SpeciesId] with AggregateRoot[Species, SpeciesEvent] {
  /**
   * Clears the backlog of uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Species = copy(uncommittedEvents = Nil)

  /**
   * Resolves a duplicate star name inconsistency.
   * @param conflictingSpeciesId Conflicting species which is keeping its name.
   * @param newName New name for this star.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Solar system with resolved star name.
   */
  def resolveDuplicateSpeciesName(conflictingSpeciesId:SpeciesId, newName: String, instanceId:InstanceId, timeStamp:Long):Species =
    applyEvent(SpeciesNameDuplicateRenamed(instanceId, timeStamp, id, conflictingSpeciesId, newName))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Solar system with event appended and new state applied.
   */
  def applyEvent(event: SpeciesEvent):Species = {
    event match {
      case event: SpeciesNameDuplicateRenamed => copy(uncommittedEvents =  uncommittedEvents :+ event)
      case event: SpeciesEvent => unhandled(event)
    }
  }
}

/**
 * Solar system factory.
 */
object Species extends ValidatedEntityAggregateFactory[Species, SpeciesEvent] {
  /**
   * Evolves a new species.
   * @param speciesId Unique new species ID.
   * @param name Unique name for the new species.
   * @param flag Flag used by species.
   * @param speciesAssetsId Media assets representing the species.
   * @param planetId Planet where the species originate.
   * @param characteristics Attributes which affect the species ability.
   * @param instanceId Instance invoking the command.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Newly evolved species.
   */
  def evolve(speciesId:SpeciesId,
             name:String,
             flag:SpeciesFlag,
             speciesAssetsId:SpeciesAssetsId,
             planetId:PlanetId,
             characteristics:SpeciesCharacteristics,
             instanceId:InstanceId,
             timeStamp:Long):Species =
    applyEvent(SpeciesEvolved(
      instanceId,
      timeStamp,
      speciesId,
      name,
      flag,
      speciesAssetsId,
      planetId,
      characteristics))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Species with event appended and new state applied.
   */
  def applyEvent(event: SpeciesEvent):Species = {
    event match {
      case event: SpeciesEvolved => Species(Nil :+ event, event.speciesId)
      case event: SpeciesEvent => unhandled(event)
    }
  }
}
