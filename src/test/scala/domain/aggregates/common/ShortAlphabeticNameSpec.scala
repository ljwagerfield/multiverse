package domain.aggregates.common

import org.specs2.mutable.Specification
import io.multiverse.domain.aggregates.common.ShortAlphabeticName

/**
 * Short alphabetic name specification.
 */
class ShortAlphabeticNameSpec extends Specification {
  "short alphabetic name" should {

    "allow uppercase leading character" in {
      val value = "Name"
      val name = ShortAlphabeticName(value)
      name.value must beEqualTo(value)
    }

    "allow 1 character" in {
      val value = "A"
      val name = ShortAlphabeticName(value)
      name.value must beEqualTo(value)
    }

    "allow up-to 15 characters" in {
      val value = "Abcdefghijklmno"
      val name = ShortAlphabeticName(value)
      name.value must beEqualTo(value)
    }

    "not allow leading lowercase characters" in {
      ShortAlphabeticName("name") must throwA[Exception]
    }

    "not allow uppercase characters beyond the leading character" in {
      ShortAlphabeticName("NAME") must throwA[Exception]
    }

    "not allow over 15 characters" in {
      ShortAlphabeticName("abcdefghijklmnop") must throwA[Exception]
    }

    "not be empty" in {
      ShortAlphabeticName("") must throwA[Exception]
    }

    "not allow numbers" in {
      ShortAlphabeticName("name123") must throwA[Exception]
    }

    "not allow leading whitespace" in {
      ShortAlphabeticName(" name") must throwA[Exception]
    }

    "not allow trailing whitespace" in {
      ShortAlphabeticName("name ") must throwA[Exception]
    }

    "not allow internal whitespace" in {
      ShortAlphabeticName("na me") must throwA[Exception]
    }
  }
}
