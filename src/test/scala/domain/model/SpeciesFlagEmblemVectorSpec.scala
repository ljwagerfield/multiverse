package domain.model

import _root_.baseSpecifications.InstanceScope
import io.multiverse.domain.model.speciesFlagEmblemVector.{SpeciesFlagEmblemVectorDefined, SpeciesFlagEmblemVectorId, SpeciesFlagEmblemVector}
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.model.common.values.Hash

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
          SpeciesFlagEmblemVectorDefined(flagEmblemId, vectorHash, instanceId, timestamp)))
    }
  }
}
