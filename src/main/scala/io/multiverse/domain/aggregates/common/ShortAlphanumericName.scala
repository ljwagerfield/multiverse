package io.multiverse.domain.aggregates.common

/**
 * Short name containing only characters in the alphabet.
 * @param value Short name value.
 */
case class ShortAlphanumericName(value:String) {
  require(value.matches("([a-z]|[A-Z]|[0-9]|\\-){1,15}"), "Name must be between 1 and 15 characters containing only characters in the range A-Z.")
}
