package io.multiverse.domain.model.user

import io.multiverse.domain.model.instance.InstanceId

/**
 * Command relating to a user of the game.
 */
trait UserCommand {
  /**
   * Instance invoking the command.
   */
  val instanceId: InstanceId

  /**
   * User the command will be issued against.
   */
  val userId: UserId
}
