package domain.model

import baseSpecifications.CommandCombinators.headCommandToTestChain
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.values.{ShortAlphabeticName, IntegralPercentage}
import io.multiverse.domain.model.resource.commands.DefineResource
import io.multiverse.domain.model.resource.{ResourceId, ResourceDefined}
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Resource specification.
 */
class ResourceSpec extends Specification {
  "resource" should {
    "be defined" in new ResourceScope {
      (DefineResource(resourceId, name, description, abundance, instanceId, timestamp)
        yields ResourceDefined(resourceId, name, description, abundance, instanceId, timestamp))
    }

    "have descriptions between 100 and 150 characters" in new ResourceScope {
      (DefineResource(resourceId, name, shortDescription, abundance, instanceId, timestamp)
        must throwA[Exception])

      (DefineResource(resourceId, name, longDescription, abundance, instanceId, timestamp)
        must throwA[Exception])
    }
  }

  /**
   * Predefined test values for resource.
   */
  trait ResourceScope extends InstanceScope {
    val resourceId = ResourceId(UUID.randomUUID)
    val name = ShortAlphabeticName("Strelitzia")
    val description = "1" * 100
    val shortDescription = "1" * 99
    val longDescription = "1" * 151
    val abundance = IntegralPercentage(1)
  }
}
