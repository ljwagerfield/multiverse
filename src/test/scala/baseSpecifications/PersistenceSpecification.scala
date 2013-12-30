package baseSpecifications

import io.multiverse.domain.model.common.commands.UnconditionalTailCommand
import io.multiverse.domain.model.common.{VolatilePersistedSet, PersistedSet, CompensationStrategy}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.stores.PersistenceStore
import org.specs2.mutable.Specification

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
   * @tparam E Event type.
   * @tparam Conflict Conflict type to resolve.
   * @tparam Command Command type used to compensate conflicts.
   */
  def verifyCompensation[E, Conflict, Command <: UnconditionalTailCommand[_]](
    changes: List[E],
    stores: List[PersistenceStore],
    getModel: PersistenceStore => VolatilePersistedSet[E, Conflict],
    compensationStrategy: CompensationStrategy[_, Conflict, Command],
    expectedConflicts: List[Conflict],
    expectedCompensations: List[Command],
    instanceId: InstanceId,
    timestamp: Long) {
    for(model <- stores.map(getModel)) {
      try {
        model.apply(changes)
        val conflicts = model.conflicts
        conflicts must containTheSameElementsAs(expectedConflicts)
        val (unresolvedConflicts, compensations) = compensationStrategy.resolve(conflicts, instanceId, timestamp)
        unresolvedConflicts must beEmpty
        compensations must containTheSameElementsAs(expectedCompensations)
      }
      finally {
        model.destroy()
      }
    }
  }
}
