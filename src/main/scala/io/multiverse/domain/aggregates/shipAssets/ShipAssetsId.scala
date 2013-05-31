package io.multiverse.domain.aggregates.shipAssets

import java.util.UUID

/**
 * Uniquely identifies the binary assets for a type of ship.
 * @param value Unique ship assets ID.
 */
case class ShipAssetsId(value: UUID)
