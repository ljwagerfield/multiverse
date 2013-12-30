package baseSpecifications

import io.multiverse.domain.model.instance.{Version, InstanceId}
import java.util.UUID
import org.specs2.specification.Scope
import io.multiverse.domain.model.user.UserId

/**
 * Predefined test values for instances.
 */
trait InstanceScope extends Scope {
  val timestamp = 0
  val instanceId = InstanceId(UUID.randomUUID)
  val version = Version(0)
  val user = UserId(UUID.randomUUID)
}
