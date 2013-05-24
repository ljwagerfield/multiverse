package io.multiverse.application.persistence

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.UserId

/**
 * Query interface for instance models hosted on the Google App Engine.
 */
abstract class GAEInstanceQueries extends InstanceQueries {
  /**
   * Identifies where this model is hosted.
   * @return Model location.
   */
  def store: GoogleAppEngineStore

  /**
   * Gets all users who are currently signed-in.
   * @return Signed-in users.
   */
  def getSignedInUsers: List[UserId] = {
    sys.error("Not implemented")
  }

  /**
   * Gets the count of users who are currently signed-in.
   * @return Number of signed-in users.
   */
  def getSignedInUserCount: Int = {
    sys.error("Not implemented")
  }

  /**
   * Gets the instance the specified user is operating within.
   * @param user Signed-in user.
   * @return Instance the specified user is signed-into.
   */
  def getUserInstance(user: UserId): InstanceId = {
    sys.error("Not implemented")
  }
}
