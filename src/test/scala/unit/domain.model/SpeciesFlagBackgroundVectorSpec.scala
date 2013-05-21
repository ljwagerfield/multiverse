package unit.domain.model

import io.multiverse.domain.model.instance.InstanceId
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
  val timestamp = 0
  val instanceId = InstanceId(UUID.randomUUID())

  "flag background" should {

    "be defined" in {
      val flagBackgroundId = SpeciesFlagBackgroundVectorId(UUID.randomUUID())
      val vectorHash = Hash.empty
      val flagBackground = SpeciesFlagBackgroundVector.define(flagBackgroundId, vectorHash, instanceId, timestamp)

      flagBackground.changes must contain(SpeciesFlagBackgroundVectorDefined(instanceId, timestamp, flagBackgroundId, vectorHash))
    }
  }
}
