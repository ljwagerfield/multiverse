package com.wagerfield.multiverse.domain.shared

/**
 * Uniquely identifies an arbitrarily large block of data.
 * @param value The hash value.
 */
case class Hash(value: Array[Byte])
