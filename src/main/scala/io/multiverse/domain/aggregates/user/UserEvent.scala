package io.multiverse.domain.aggregates.user

import io.multiverse.domain.aggregates.instance.InstanceEvent

/**
 * Event relating to a registered user within the game.
 */
trait UserEvent extends InstanceEvent {
  val userId: UserId
}
