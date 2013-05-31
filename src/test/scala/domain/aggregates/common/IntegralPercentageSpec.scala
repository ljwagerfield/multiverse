package domain.aggregates.common

import org.specs2.mutable.Specification
import io.multiverse.domain.aggregates.common.IntegralPercentage

/**
 * Integral percentage specification.
 */
class IntegralPercentageSpec extends Specification {
  "integral percentage" should {

    "be greater or equal to zero" in {
      IntegralPercentage(0).value must beEqualTo(0)
      IntegralPercentage(1).value must beEqualTo(1)
    }

    "be less than or equal to 100" in {
      IntegralPercentage(100).value must beEqualTo(100)
      IntegralPercentage(99).value must beEqualTo(99)
    }

    "not be less than zero" in {
      IntegralPercentage(-1) must throwA[Exception]
    }

    "not be greater than 100" in {
      IntegralPercentage(-1) must throwA[Exception]
    }
  }
}
