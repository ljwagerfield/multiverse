package io.multiverse.application.persistence

/**
 * Persistence store hosted on the Google App Engine.
 */
case class GoogleAppEngineStore extends PersistenceStore {

}

/**
 * Persistence store factory.
 */
object GoogleAppEngineStore {
  /**
   * Generates a reference to a non-existent store.
   * @return Random store.
   */
  def randomStore: GoogleAppEngineStore = {
    // Generate temporary path.
    sys.error("Not implemented")
  }
}
