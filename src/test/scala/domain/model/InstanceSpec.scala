package domain.model

import baseSpecifications.CommandSpecification
import io.multiverse.domain.model.common.commands.CommandConversions._
import io.multiverse.domain.model.instance.commands.{SignOut, SignIn, CreateInstance}
import io.multiverse.domain.model.instance.{InstanceEvent, UserSignedOut, UserSignedIn, InstanceCreated, Version, Instance, InstanceId}
import io.multiverse.domain.model.user.UserId
import java.util.UUID
import org.specs2.specification.Scope
import io.multiverse.domain.model.common.{EventSourced, AggregateRoot}

/**
 * Instance specification.
 */
class InstanceSpec extends CommandSpecification {
  "new instance" should {
    "be created" in new InstanceScope {
      assertSuccess[Instance, InstanceEvent, CreateInstance](
        CreateInstance(version, instanceId, timestamp),
        List(InstanceCreated(instanceId, version, timestamp)))
    }
  }

  "instance" should {
    "support signed-ins" in new CreatedInstanceScope {
      assertSuccess[Instance, InstanceEvent, SignIn](
        createdInstance,
        SignIn(user, instanceId, timestamp),
        List(UserSignedIn(instanceId, user, timestamp)))
    }

    "support superfluous signed-outs" in new CreatedInstanceScope {
      assertIdempotent[Instance, InstanceEvent, SignOut](
        createdInstance,
        SignOut(instanceId, timestamp))
    }
  }

  "signed-in instance" should {
    "support idempotent sign-ins from the same user" in new SignedInInstanceScope {
      assertIdempotent[Instance, InstanceEvent, SignIn](
        signedInInstance,
        SignIn(user, instanceId, timestamp + 1))
    }

    "not support sign-ins from a different user" in new SignedInInstanceScope {
      val differentUser = UserId(UUID.randomUUID)
      assertFailure[Instance, InstanceEvent, SignIn](
        signedInInstance,
        SignIn(differentUser, instanceId, timestamp),
        c => List(SignIn.SignedInUserSingularity(c)))
    }

    "support sign-outs" in new SignedInInstanceScope {
      assertUnconditional[Instance, InstanceEvent, SignOut](
        signedInInstance,
        SignOut(instanceId, timestamp),
        List(UserSignedOut(instanceId, timestamp)))
    }

    "support idempotent sign-outs" in new SignedInInstanceScope {
      assertIdempotent[Instance, InstanceEvent, SignOut](
        (signedInInstance /> SignOut(instanceId, timestamp)).markCommitted,
        SignOut(instanceId, timestamp + 1))
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
    val t = CreateInstance(version, instanceId, timestamp) /> SignIn(user, instanceId, timestamp)
    val createdInstance = CreateInstance(version, instanceId, timestamp).markCommitted
  }

  /**
   * Predefined test values for a signed-in instance.
   */
  trait SignedInInstanceScope extends CreatedInstanceScope {
    val signedInInstance = (createdInstance /> SignIn(user, instanceId, timestamp)).markCommitted
  }
}
