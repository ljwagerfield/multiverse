package domain.aggregates

import _root_.common.InstanceScope
import io.multiverse.domain.aggregates.resource.{ResourceId, ResourceDefined, Resource}
import java.util.UUID
import org.specs2.mutable.Specification
import io.multiverse.domain.aggregates.common.{IntegralPercentage, ShortAlphabeticName}

/**
 * Resource specification.
 */
class ResourceSpec extends Specification {
  "resource" should {
    "be defined" in new ResourceScope {
      Resource
        .define(resourceId, name, description, abundance, instanceId, timestamp)
        .changes must beEqualTo(List(
          ResourceDefined(resourceId, name, description, abundance, instanceId, timestamp)))
    }

    "have descriptions between 100 and 150 characters" in new ResourceScope {
      Resource.define(resourceId, name, shortDescription, abundance, instanceId, timestamp) must throwA[Exception]
      Resource.define(resourceId, name, longDescription, abundance, instanceId, timestamp) must throwA[Exception]
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
