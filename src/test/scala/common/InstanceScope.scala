package common

import io.multiverse.domain.aggregates.instance.InstanceId
import java.util.UUID
import org.specs2.specification.Scope

/**
 * Predefined test values for instances.
 */
trait InstanceScope extends Scope {
  val timestamp = 0
  val instanceId = InstanceId(UUID.randomUUID)
}
