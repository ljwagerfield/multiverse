package io.multiverse.domain.aggregates.user

import io.multiverse.domain.aggregates.instance.InstanceCommand

/**
 * Command relating to a registered user within the game.
 */
trait UserCommand extends InstanceCommand {
  val userId: UserId
  val prerequisites: List[User => Boolean]
}
