package domain.model.common.values

import io.multiverse.domain.model.common.values.Hash
import org.specs2.mutable.Specification

/**
 * Hash specification.
 */
class HashSpec extends Specification {
  "hash" should {

    "contain 256 bits" in {
      val value = List.fill(32)(0.toByte)
      val hash = Hash(value)
      hash.value must beEqualTo(value)
    }

    "not contain more than 256 bits" in {
      val value = List.fill(33)(0.toByte)
      Hash(value) must throwA[Exception]
    }

    "not contain fewer than 256 bits" in {
      val value = List.fill(31)(0.toByte)
      Hash(value) must throwA[Exception]
    }

    "not be empty" in {
      val value = Nil
      Hash(value) must throwA[Exception]
    }
  }
}
