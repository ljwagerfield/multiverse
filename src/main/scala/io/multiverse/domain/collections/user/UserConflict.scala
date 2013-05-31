package io.multiverse.domain.collections.user

import io.multiverse.domain.aggregates.user.UserId

/**
 * Conflict relating to a user aggregates.
 */
trait UserConflict {
  val userId: UserId
}
