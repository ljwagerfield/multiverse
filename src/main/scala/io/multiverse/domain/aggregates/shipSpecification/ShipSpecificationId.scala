package io.multiverse.domain.aggregates.shipSpecification

import java.util.UUID

/**
 * Uniquely identifies a ship specification.
 * @param value Unique specification ID.
 */
case class ShipSpecificationId(value: UUID)
