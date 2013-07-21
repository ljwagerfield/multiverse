package io.multiverse.domain.aggregates.user

import io.multiverse.domain.aggregates.instance.InstanceId

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
