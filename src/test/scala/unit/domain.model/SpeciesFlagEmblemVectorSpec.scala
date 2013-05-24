package unit.domain.model

import _root_.shared.InstanceScope
import io.multiverse.domain.model.speciesFlagEmblemVector.{SpeciesFlagEmblemVectorDefined, SpeciesFlagEmblemVectorId, SpeciesFlagEmblemVector}
import io.multiverse.domain.shared.Hash
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Species flag emblem vector specification.
 */
class SpeciesFlagEmblemVectorSpec extends Specification {
  "flag emblem" should {
    "be defined" in new InstanceScope {
      val flagEmblemId = SpeciesFlagEmblemVectorId(UUID.randomUUID())
      val vectorHash = Hash.empty

      SpeciesFlagEmblemVector
        .define(flagEmblemId, vectorHash, instanceId, timestamp)
        .changes must beEqualTo(List(
          SpeciesFlagEmblemVectorDefined(instanceId, timestamp, flagEmblemId, vectorHash)))
    }
  }
}
