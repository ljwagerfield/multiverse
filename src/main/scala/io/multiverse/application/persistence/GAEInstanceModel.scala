package io.multiverse.application.persistence

import io.multiverse.domain.model.instance.InstanceEvent

/**
 * Instance model hosted in a Google App Engine store.
 */
abstract class GAEInstanceModel extends InstanceModel {
  /**
   * Identifies where this model is hosted.
   * @return Model location.
   */
  def store: GoogleAppEngineStore

  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to denormalize.
   */
  def denormalize(changes: List[InstanceEvent]) {}

  /**
   * Removes the model from the associated store.
   */
  def destroy() {}
}

/**
 * Instance model factory for the Google App Engine.
 */
object GAEInstanceModel {
  /**
   * Creates an instance model in the provided store. Method has not effect if the model already exists.
   * @param googleAppEngineStore Persistence store.
   * @return Model for the given store.
   */
  def createIn(googleAppEngineStore:GoogleAppEngineStore):GAEInstanceModel = {
    // Create appropriate tables in store.
    new GAEInstanceModel {
      override def store = googleAppEngineStore
    }
  }
}
