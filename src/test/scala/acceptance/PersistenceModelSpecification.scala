package acceptance

import io.multiverse.application.persistence.{PersistenceModelWithConflicts, PersistenceStore, PersistenceModel}
import org.specs2.mutable.Specification

/**
 * Base class for persistence model specifications.
 */
abstract class PersistenceModelSpecification extends Specification {
  /**
   * Denormalizes changes into temporary models for each store, verifying conflicts where necessary.
   * @param changes Event sequence to denormalize.
   * @param stores Stores to denormalize events into.
   * @param getModel Returns a new model for the given store.
   * @tparam E Event type.
   * @tparam M Model type.
   */
  def verifyChanges[E, M <: PersistenceModel[E]](changes: List[E], stores: List[PersistenceStore], getModel: PersistenceStore => M, verify: PersistenceStore => Unit) {
    for(model <- stores.map(getModel)) {
      try {
        model.denormalize(changes)
        verify(model.store)
      }
      finally {
        model.destroy()
      }
    }
  }

  /**
   * Denormalizes changes into temporary models for each store, verifying conflicts where necessary.
   * @param changes Event sequence to denormalize.
   * @param stores Stores to denormalize events into.
   * @param getModel Returns a new model for the given store.
   * @tparam E Event type.
   * @tparam M Model type.
   */
  def verifyChanges[E, M <: PersistenceModel[E]](changes: List[E], stores: List[PersistenceStore], getModel: PersistenceStore => M) {
    for(model <- stores.map(getModel)) {
      try {
        model.denormalize(changes)
      }
      finally {
        model.destroy()
      }
    }
  }


  /**
   * Denormalizes changes into temporary models for each store, verifying conflicts where necessary.
   * @param changes Event sequence to denormalize.
   * @param expectedConflicts Conflicts expected from denormalizing the given events.
   * @param stores Stores to denormalize events into.
   * @param getModel Returns a new model for the given store.
   * @tparam E Event type.
   * @tparam C Conflict type.
   * @tparam M Model type.
   */
  def verifyCombinedChanges[E, C, M <: PersistenceModelWithConflicts[E, C]](changes: List[E], expectedConflicts: List[C], stores: List[PersistenceStore], getModel: PersistenceStore => M) {
    for(model <- stores.map(getModel)) {
      try {
        model.denormalize(changes)
        model.conflicts must contain(expectedConflicts)
      }
      finally {
        model.destroy()
      }
    }
  }
}
