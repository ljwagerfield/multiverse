package io.multiverse.domain.model.user


/**
 * Conflict relating to a user aggregates.
 */
trait UserConflict {
  val userId: UserId
}
