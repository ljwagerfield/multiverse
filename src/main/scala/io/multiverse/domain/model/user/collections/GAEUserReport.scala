package io.multiverse.domain.model.user.collections

import io.multiverse.domain.stores.GoogleAppEngineStore
import io.multiverse.domain.model.common.values.Email

/**
 * Query interface for user models hosted on the Google App Engine.
 */
class GAEUserReport(val store: GoogleAppEngineStore) extends UserReport {
  /**
   * Tests the availability of the provided email.
   * @param email Email to check.
   * @return True if email taken. False if available.
   */
  def isEmailTaken(email: Email): Boolean = {
    sys.error("Not implemented")
  }
}
