package io.multiverse.domain.aggregates.shipResearch

import java.util.UUID

/**
 * Uniquely identifies a researched weapon.
 * @param value Unique weapon ID.
 */
case class WeaponId(value: UUID)
