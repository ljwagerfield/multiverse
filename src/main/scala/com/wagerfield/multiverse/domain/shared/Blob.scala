package com.wagerfield.multiverse.domain.shared

/**
 * Binary large object.
 * @param hash Uniquely identifies the content of this BLOB.
 * @param value The BLOB content.
 */
case class Blob(hash:Hash, value: Array[Byte])