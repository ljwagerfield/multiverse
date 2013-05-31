package io.multiverse.domain.aggregates.shipResearch

import java.util.UUID

/**
 * Uniquely identifies a researched shield.
 * @param value Unique shield ID.
 */
case class ShieldId(value: UUID)
