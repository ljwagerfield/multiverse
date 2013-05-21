package unit.domain.model

import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import io.multiverse.domain.model.instance.{UserSignedOut, UserSignedIn, InstanceCreated, Version, Instance, InstanceId}
import java.util.UUID
import io.multiverse.domain.model.species.SpeciesId

/**
 * Instance specification.
 */
class InstanceSpec extends Specification {
  "instance" should {
    "be created" in new InstanceScope {
      Instance
        .create(instanceId, timestamp, version)
        .changes must contain(InstanceCreated(instanceId, timestamp, version))
    }

    "be signed-into by a user" in new InstanceScope {
      Instance
        .create(instanceId, timestamp, version)
        .signIn(user, timestamp)
        .changes must contain(UserSignedIn(instanceId, timestamp, user))
    }
  }

  "signed-in instance" should {
    "support idempotent sign-ins from the same user" in new SignedInInstanceScope {
      signedInInstance
        .signIn(user, timestamp + 1)
        .changes must contain(UserSignedIn(instanceId, timestamp, user))
    }

    "not support sign-ins from a different user" in new SignedInInstanceScope {
      val differentUser = SpeciesId(UUID.randomUUID)
      signedInInstance.signIn(differentUser, timestamp) must throwA[Exception]
    }

    "support sign-outs" in new SignedInInstanceScope {
      signedInInstance
        .signOut(timestamp)
        .changes must contain(UserSignedOut(instanceId, timestamp))
    }

    "support idempotent sign-outs" in new SignedInInstanceScope {
      signedInInstance
        .signOut(timestamp)
        .signOut(timestamp + 1)
        .changes must contain(UserSignedOut(instanceId, timestamp))
    }
  }

  /**
   * Predefined test values for instance.
   */
  trait InstanceScope extends Scope {
    val timestamp = 0
    val instanceId = InstanceId(UUID.randomUUID)
    val version = Version(0)
    val user = SpeciesId(UUID.randomUUID)
  }

  /**
   * Predefined test values for a signed-in instance.
   */
  trait SignedInInstanceScope extends InstanceScope {
    val signedInInstance = Instance
      .create(instanceId, timestamp, version)
      .signIn(user, timestamp)
  }
}
