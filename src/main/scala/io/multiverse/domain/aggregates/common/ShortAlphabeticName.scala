package io.multiverse.domain.aggregates.common

/**
 * Short name containing only characters in the alphabet.
 * @param value Short name value.
 */
case class ShortAlphabeticName(value:String) {
  require(value.matches("[A-Z]([a-z]){0,14}"), "Name must be between 1 and 15 characters, proper-cased, containing only characters in the range A-Z.")
}
