package domain.model

import baseSpecifications.CommandCombinators.headCommandToTestChain
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.speciesFlagEmblemVector.commands.DefineSpeciesFlagEmblemVector
import io.multiverse.domain.model.speciesFlagEmblemVector.{SpeciesFlagEmblemVectorDefined, SpeciesFlagEmblemVectorId}
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

      (DefineSpeciesFlagEmblemVector(flagEmblemId, vectorHash, instanceId, timestamp)
        yields SpeciesFlagEmblemVectorDefined(flagEmblemId, vectorHash, instanceId, timestamp))
    }
  }
}
