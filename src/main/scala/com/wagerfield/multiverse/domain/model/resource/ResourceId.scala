package com.wagerfield.multiverse.domain.model.resource

import java.util.UUID

/**
 * Uniquely identifies a resource which can be harvested and traded within the universe.
 * @param value Unique resource ID.
 */
case class ResourceId(value: UUID)