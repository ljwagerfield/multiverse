package domain.model

import baseSpecifications.CommandCombinators.headCommandToTestChain
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.values.Hash
import io.multiverse.domain.model.speciesFlagBackgroundVector.commands.DefineSpeciesFlagBackgroundVector
import io.multiverse.domain.model.speciesFlagBackgroundVector.{SpeciesFlagBackgroundVectorDefined, SpeciesFlagBackgroundVectorId}
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

      (DefineSpeciesFlagBackgroundVector(flagBackgroundId, vectorHash, instanceId, timestamp)
        yields SpeciesFlagBackgroundVectorDefined(flagBackgroundId, vectorHash, instanceId, timestamp))
    }
  }
}
