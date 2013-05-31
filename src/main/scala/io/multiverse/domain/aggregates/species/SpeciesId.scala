package io.multiverse.domain.aggregates.species

import java.util.UUID

/**
 * Uniquely identifies a species entity.
 * @param value Unique species ID.
 */
case class SpeciesId(value: UUID)
