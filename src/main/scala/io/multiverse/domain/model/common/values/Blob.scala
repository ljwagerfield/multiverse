package io.multiverse.domain.model.common.values

/**
 * Binary large object.
 * @param hash Uniquely identifies the content of this BLOB.
 * @param value The BLOB content.
 */
case class Blob(hash:Hash, value: List[Byte])

/**
 * Contains an empty BLOB value.
 */
object Blob {
  val size = 32
  val empty = Blob(Hash(List.fill(size)(0.toByte)), Nil)
}
