package io.multiverse.domain.shared

import org.apache.commons.validator.routines.EmailValidator

case class Email(value: String) {
  require(value.charAt(0) > ' ' && value.charAt(value.length - 1) > ' ', "Email must not contain trailing or leading whitespace.")
  require(EmailValidator.getInstance().isValid(value), "Email must conform to RFC 822.")
}
