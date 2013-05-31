package io.multiverse.domain.collections.user

import io.multiverse.domain.aggregates.common.Email
import io.multiverse.domain.stores.GoogleAppEngineStore

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
