package io.multiverse.domain.aggregates.shipResearch

import java.util.UUID

/**
 * Uniquely identifies a researched engine.
 * @param value Unique engine ID.
 */
case class EngineId(value: UUID)
