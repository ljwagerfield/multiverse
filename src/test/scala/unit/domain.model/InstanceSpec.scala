package unit.domain.model

import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import io.multiverse.domain.model.instance.{UserSignedOut, UserSignedIn, InstanceCreated, Version, Instance, InstanceId}
import java.util.UUID
import io.multiverse.domain.model.species.SpeciesId
import io.multiverse.domain.model.user.UserId

/**
 * Instance specification.
 */
class InstanceSpec extends Specification {
  "new instance" should {
    "be created" in new InstanceScope {
      Instance
        .create(instanceId, timestamp, version)
        .changes must beEqualTo(List(
          InstanceCreated(instanceId, timestamp, version)))
    }
  }

  "instance" should {
    "support signed-ins" in new CreatedInstanceScope {
      createdInstance
        .signIn(user, timestamp)
        .changes must beEqualTo(List(
          UserSignedIn(instanceId, timestamp, user)))
    }

    "support superfluous signed-outs" in new CreatedInstanceScope {
      createdInstance
        .signOut(timestamp)
        .changes must beEmpty
    }
  }

  "signed-in instance" should {
    "support idempotent sign-ins from the same user" in new SignedInInstanceScope {
      signedInInstance
        .signIn(user, timestamp + 1)
        .changes must beEmpty
    }

    "not support sign-ins from a different user" in new SignedInInstanceScope {
      val differentUser = UserId(UUID.randomUUID)
      signedInInstance.signIn(differentUser, timestamp) must throwA[Exception]
    }

    "support sign-outs" in new SignedInInstanceScope {
      signedInInstance
        .signOut(timestamp)
        .changes must beEqualTo(List(
          UserSignedOut(instanceId, timestamp)))
    }

    "support idempotent sign-outs" in new SignedInInstanceScope {
      signedInInstance
        .signOut(timestamp)
        .signOut(timestamp + 1)
        .changes must beEqualTo(List(
          UserSignedOut(instanceId, timestamp)))
    }
  }

  /**
   * Predefined test values for instance.
   */
  trait InstanceScope extends Scope {
    val timestamp = 0
    val instanceId = InstanceId(UUID.randomUUID)
    val version = Version(0)
    val user = UserId(UUID.randomUUID)
  }

  /**
   * Predefined test values for created instance.
   */
  trait CreatedInstanceScope extends InstanceScope {
    val createdInstance = Instance.create(instanceId, timestamp, version).markCommitted
  }

  /**
   * Predefined test values for a signed-in instance.
   */
  trait SignedInInstanceScope extends CreatedInstanceScope {
    val signedInInstance = createdInstance.signIn(user, timestamp).markCommitted
  }
}
