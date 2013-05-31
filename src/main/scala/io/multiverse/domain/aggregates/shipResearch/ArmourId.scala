package io.multiverse.domain.aggregates.shipResearch

import java.util.UUID

/**
 * Uniquely identifies researched armour.
 * @param value Unique armour ID.
 */
case class ArmourId(value: UUID)
