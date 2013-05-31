package io.multiverse.domain.aggregates.shipResearch

import java.util.UUID

/**
 * Uniquely identifies a researched colonizing unit.
 * @param value Unique colonizing unit ID.
 */
case class ColonizingUnitId(value: UUID)
