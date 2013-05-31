package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.speciesFlagBackgroundVector.{SpeciesFlagBackgroundVectorDefined,
                                                                           SpeciesFlagBackgroundVectorId,
                                                                           SpeciesFlagBackgroundVector}
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.aggregates.common.Hash

/**
 * Species flag background vector specification.
 */
class SpeciesFlagBackgroundVectorSpec extends Specification {
  "flag background" should {
    "be defined" in new InstanceScope {
      val flagBackgroundId = SpeciesFlagBackgroundVectorId(UUID.randomUUID())
      val vectorHash = Hash.empty

      SpeciesFlagBackgroundVector
        .define(flagBackgroundId, vectorHash, instanceId, timestamp)
        .changes must beEqualTo(List(
          SpeciesFlagBackgroundVectorDefined(instanceId, timestamp, flagBackgroundId, vectorHash)))
    }
  }
}
