package unit.domain.model

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.speciesFlagEmblemVector.{SpeciesFlagEmblemVectorDefined, SpeciesFlagEmblemVectorId, SpeciesFlagEmblemVector}
import io.multiverse.domain.shared.Hash
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Species flag emblem vector specification.
 */
class SpeciesFlagEmblemVectorSpec extends Specification {
  val timestamp = 0
  val instanceId = InstanceId(UUID.randomUUID())

  "flag emblem" should {

    "be defined" in {
      val flagEmblemId = SpeciesFlagEmblemVectorId(UUID.randomUUID())
      val vectorHash = Hash.empty
      val flagEmblem = SpeciesFlagEmblemVector.define(flagEmblemId, vectorHash, instanceId, timestamp)

      flagEmblem.changes must contain(SpeciesFlagEmblemVectorDefined(instanceId, timestamp, flagEmblemId, vectorHash))
    }
  }
}
