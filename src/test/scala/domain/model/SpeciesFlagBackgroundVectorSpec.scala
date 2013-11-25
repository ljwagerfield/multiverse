package domain.model

import _root_.baseSpecifications.InstanceScope
import io.multiverse.domain.model.speciesFlagBackgroundVector.{SpeciesFlagBackgroundVectorDefined,
                                                                           SpeciesFlagBackgroundVectorId,
                                                                           SpeciesFlagBackgroundVector}
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.model.common.values.Hash

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
          SpeciesFlagBackgroundVectorDefined(flagBackgroundId, vectorHash, instanceId, timestamp)))
    }
  }
}
