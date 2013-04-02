package com.wagerfield.multiverse.domain.shared

/**
 * Short name containing only characters in the alphabet.
 * @param value Short name value.
 */
case class ShortAlphabeticName(value:String) {
  require(value.matches("([a-z][A-Z]){1,15}"), "Name must be between 1 and 15 characters containing only characters in the range A-Z.")
}
