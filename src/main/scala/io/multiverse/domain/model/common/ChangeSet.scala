package io.multiverse.domain.model.common

/**
 * Backlog of changes that can be committed.
 * @tparam C Change type.
 * @tparam R Committed result type.
 */
trait ChangeSet[+C, +R] {

  /**
   * Sequence of changes.
   */
  val changes: List[C]

  /**
   * State after committing to the changes.
   */
  val committed: R
}
