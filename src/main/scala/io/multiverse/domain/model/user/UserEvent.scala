package io.multiverse.domain.model.user

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Event relating to a registered user within the game.
 */
trait UserEvent extends InstanceEvent {
  val userId: UserId
}
