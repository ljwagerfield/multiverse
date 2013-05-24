package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.speciesFlagBackgroundVector.{SpeciesFlagBackgroundVectorDefined,
                                                                           SpeciesFlagBackgroundVectorId,
                                                                           SpeciesFlagBackgroundVector}
import io.multiverse.domain.shared.Hash
import java.util.UUID
import org.specs2.mutable.Specification

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
