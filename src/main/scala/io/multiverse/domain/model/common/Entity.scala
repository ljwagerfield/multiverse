package io.multiverse.domain.model.common

/**
 * Object that is not defined by its attributes, but rather by a thread of continuity and its identity.
 * @tparam T The ID type.
 */
trait Entity[T] {
  val id: T
}
