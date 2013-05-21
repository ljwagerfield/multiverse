package unit.domain.model.shared

import org.specs2.mutable.Specification
import io.multiverse.domain.shared.ShortAlphanumericName

/**
  * Short alphanumeric name specification.
  */
class ShortAlphanumericNameSpec extends Specification {
  "short alphanumeric name" should {

    "allow lowercase characters" in {
      val value = "name"
      val name = ShortAlphanumericName(value)
      name.value must beEqualTo(value)
    }

    "allow uppercase characters" in {
      val value = "NAME"
      val name = ShortAlphanumericName(value)
      name.value must beEqualTo(value)
     }

    "allow mixed case characters" in {
      val value = "Name"
      val name = ShortAlphanumericName(value)
      name.value must beEqualTo(value)
    }

    "allow numbers" in {
      val value = "name123"
      val name = ShortAlphanumericName(value)
      name.value must beEqualTo(value)
    }

    "allow hyphens" in {
      val value = "name-123"
      val name = ShortAlphanumericName(value)
      name.value must beEqualTo(value)
    }

    "allow 1 character" in {
      val value = "a"
      val name = ShortAlphanumericName(value)
      name.value must beEqualTo(value)
    }

    "allow upto 15 characters" in {
      val value = "abcdefghijklmno"
      val name = ShortAlphanumericName(value)
      name.value must beEqualTo(value)
    }

    "not allow over 15 characters" in {
      ShortAlphanumericName("abcdefghijklmnop") must throwA[Exception]
    }

    "not be empty" in {
      ShortAlphanumericName("") must throwA[Exception]
    }

    "not allow leading whitespace" in {
      ShortAlphanumericName(" name") must throwA[Exception]
    }

    "not allow trailing whitespace" in {
      ShortAlphanumericName("name ") must throwA[Exception]
    }

    "not allow internal whitespace" in {
      ShortAlphanumericName("na me") must throwA[Exception]
    }
  }
}
