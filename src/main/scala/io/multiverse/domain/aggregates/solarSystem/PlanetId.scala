package io.multiverse.domain.aggregates.solarSystem

import java.util.UUID

/**
 * Uniquely identifies a planet entity.
 * @param value Unique planet ID.
 */
case class PlanetId(value: UUID)
