package io.multiverse.domain.aggregates.speciesFlagEmblemVector

import java.util.UUID

/**
 * Uniquely identifies binary vector asset for the emblem on a species flag.
 * @param value Unique flag emblem vector ID.
 */
case class SpeciesFlagEmblemVectorId(value: UUID)
