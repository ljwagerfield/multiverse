package io.multiverse.domain.shared

/**
 * Uniquely identifies an arbitrarily large block of data.
 * @param value The SHA-256 hash value.
 */
case class Hash(value: List[Byte]) {
  require(value.length == Hash.size, "Value must be a valid SHA-256 hash.")
}

/**
 * Contains an empty hash value.
 */
object Hash {
  val size = 32
  val empty = Hash(List.fill(size)(0.toByte))
}
