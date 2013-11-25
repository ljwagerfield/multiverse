package io.multiverse.domain.model.user

import io.multiverse.domain.model.instance.InstanceId

/**
 * Event relating to a registered user within the game.
 */
trait UserEvent {

  /**
   * Instance that invoked the command that raised this event.
   */
  val instanceId: InstanceId

  /**
   * User the command will be issued against.
   */
  val userId: UserId
}
