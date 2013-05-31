package io.multiverse.domain.aggregates.ship

import java.util.UUID

/**
 * Uniquely identifies a ship entity.
 * @param value Unique ship ID.
 */
case class ShipId(value: UUID)
