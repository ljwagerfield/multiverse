package common

import io.multiverse.domain.collections.common.{CompensationStrategy, VolatilePersistedSet, PersistedSet}
import org.specs2.mutable.Specification
import io.multiverse.domain.stores.PersistenceStore

/**
 * Base class for persistence model specifications.
 */
abstract class PersistenceSpecification extends Specification {
  /**
   * Denormalizes changes into temporary models for each store, verifying conflicts where necessary.
   * @param changes Event sequence to apply.
   * @param stores Stores to apply events into.
   * @param getModel Returns a new model for the given store.
   * @tparam E Event type.
   */
  def applyState[E](changes: List[E], stores: List[PersistenceStore], getModel: PersistenceStore => PersistedSet[E]) {
    for(model <- stores.map(getModel)) {
      try {
        model.apply(changes)
      }
      finally {
        model.destroy()
      }
    }
  }

  /**
   * Denormalizes changes into temporary models for each store, verifying conflicts where necessary.
   * @param changes Event sequence to apply.
   * @param stores Stores to apply events into.
   * @param getModel Returns a new model for the given store.
   * @tparam E Event type.
   */
  def verifyState[E](changes: List[E], stores: List[PersistenceStore], getModel: PersistenceStore => PersistedSet[E], verify: PersistenceStore => Unit) {
    for(model <- stores.map(getModel)) {
      try {
        model.apply(changes)
        verify(model.store)
      }
      finally {
        model.destroy()
      }
    }
  }


  /**
   * Denormalizes changes into temporary models for each store, verifying conflicts where necessary.
   * @param changes Event sequence to apply.
   * @param expectedConflicts Conflicts expected from denormalizing the given events.
   * @param stores Stores to apply events into.
   * @param getModel Returns a new model for the given store.
   * @tparam E Event type.
   * @tparam Conflict Conflict type.
   */
  def verifyConflicts[E, Conflict, Command](changes: List[E], expectedConflicts: List[Conflict], stores: List[PersistenceStore], getModel: PersistenceStore => VolatilePersistedSet[E, Conflict], resolutionStrategy: CompensationStrategy[Conflict, Command]) {
    for(model <- stores.map(getModel)) {
      try {
        model.apply(changes)
        val conflicts = model.conflicts
        conflicts must containTheSameElementsAs(expectedConflicts)
        val (unresolvedConflicts, compensations) = resolutionStrategy.resolve(conflicts)
        unresolvedConflicts must beEmpty
      }
      finally {
        model.destroy()
      }
    }
  }
}
