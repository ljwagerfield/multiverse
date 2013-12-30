package domain.model

import baseSpecifications.CommandCombinators.{chainToTestChain, headCommandToTestChain}
import baseSpecifications.InstanceScope
import io.multiverse.domain.model.common.commands.CommandCombinators.headCommandToChain
import io.multiverse.domain.model.common.commands.Commit
import io.multiverse.domain.model.instance.commands.{SignOut, SignIn, CreateInstance}
import io.multiverse.domain.model.instance.{UserSignedOut, UserSignedIn, InstanceCreated}
import io.multiverse.domain.model.user.UserId
import java.util.UUID
import org.specs2.mutable.Specification

/**
 * Instance specification.
 */
class InstanceSpec extends Specification {
  "new instance" should {
    "be created" in new InstanceScope {
      (CreateInstance(version, instanceId, timestamp)
        yields InstanceCreated(instanceId, version, timestamp))
    }
  }

  "instance" should {
    "support signed-ins" in new CreatedInstanceScope {
      (createdInstance
        after SignIn(user, instanceId, timestamp)
        yields UserSignedIn(instanceId, user, timestamp))
    }

    "support superfluous signed-outs" in new CreatedInstanceScope {
      (createdInstance
        after SignOut(instanceId, timestamp)
        yields Nil)
    }
  }

  "signed-in instance" should {
    "support idempotent sign-ins from the same user" in new SignedInInstanceScope {
      (signedInInstance
        after SignIn(user, instanceId, timestamp + 1)
        yields Nil)
    }

    "not support sign-ins from a different user" in new SignedInInstanceScope {
      val differentUser = UserId(UUID.randomUUID)
      (signedInInstance
        cannot SignIn(differentUser, instanceId, timestamp)
        because SignIn.SignedInUserSingularity)
    }

    "support sign-outs" in new SignedInInstanceScope {
      (signedInInstance
        after SignOut(instanceId, timestamp)
        yields UserSignedOut(instanceId, timestamp))
    }

    "support idempotent sign-outs" in new SignedInInstanceScope {
      (signedInInstance
        after SignOut(instanceId, timestamp)
        after Commit()
        after SignOut(instanceId, timestamp + 1)
        yields Nil)
    }
  }

  /**
   * Predefined test values for created instance.
   */
  trait CreatedInstanceScope extends InstanceScope {
    val createdInstance = CreateInstance(version, instanceId, timestamp) after Commit()
  }

  /**
   * Predefined test values for a signed-in instance.
   */
  trait SignedInInstanceScope extends CreatedInstanceScope {
    val signedInInstance = createdInstance after SignIn(user, instanceId, timestamp) after Commit()
  }
}
