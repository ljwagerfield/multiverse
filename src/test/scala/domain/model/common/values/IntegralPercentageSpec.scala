package domain.model.common.values

import io.multiverse.domain.model.common.values.IntegralPercentage
import org.specs2.mutable.Specification

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
