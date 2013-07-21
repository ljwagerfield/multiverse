package common

import io.multiverse.domain.collections.common.{CompensationStrategy, VolatilePersistedSet, PersistedSet}
import org.specs2.mutable.Specification
import io.multiverse.domain.stores.PersistenceStore
import io.multiverse.domain.aggregates.common.{UnconditionalCommand, AggregateRoot}

/**
 * Base class for persistence model specifications.
 */
trait PersistenceSpecification extends Specification {
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
   * @tparam A Aggregate type.
   * @tparam E Event type.
   * @tparam Conflict Conflict type.
   * @tparam Command Command type.
   */
  def verifyCompensation[A <: AggregateRoot[A, E], E, Conflict, Command <: UnconditionalCommand[A, E]](
                                                changes: List[E],
                                                stores: List[PersistenceStore],
                                                getModel: PersistenceStore => VolatilePersistedSet[E, Conflict],
                                                compensationStrategy: CompensationStrategy[A, E, Conflict, Command],
                                                expectedConflicts: List[Conflict],
                                                expectedCompensations: List[Command]) {
    for(model <- stores.map(getModel)) {
      try {
        model.apply(changes)
        val conflicts = model.conflicts
        conflicts must containTheSameElementsAs(expectedConflicts)
        val (unresolvedConflicts, compensations) = compensationStrategy resolve conflicts
        unresolvedConflicts must beEmpty
        compensations must containTheSameElementsAs(expectedCompensations)
      }
      finally {
        model.destroy()
      }
    }
  }
}