package unit.domain.model

import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import io.multiverse.domain.model.instance.InstanceId
import java.util.UUID
import io.multiverse.domain.model.user.{UserEmailVerified, UserRegistered, UserId, User}
import io.multiverse.domain.shared.{Hash, Email}

/**
 * User specification.
 */
class UserSpec extends Specification {
  "user" should {
    "be registered" in new UserScope {
      User
        .register(userId, email, password, verificationCode, instanceId, timestamp)
        .changes must beEqualTo(List(
          UserRegistered(instanceId, timestamp, userId, email, password, verificationCode)))
    }
  }

  "registered user" should {
    "verify their email address with a valid code" in new RegisteredUserScope {
      registeredUser
        .verifyEmail(verificationCode, instanceId, timestamp)
        .changes must beEqualTo(List(
          UserEmailVerified(instanceId, timestamp, userId)))
    }

    "not verify their email address with an invalid code" in new RegisteredUserScope {
      val invalidCode = Hash(List.fill(Hash.size)(1.toByte))
      registeredUser
        .verifyEmail(invalidCode, instanceId, timestamp) must throwA[Exception]
    }
  }

  /**
   * Predefined test values for user.
   */
  trait UserScope extends Scope {
    val timestamp = 0
    val instanceId = InstanceId(UUID.randomUUID)
    val userId = UserId(UUID.randomUUID)
    val email = Email("lawrence@wagerfield.com")
    val password = Hash.empty
    val verificationCode = Hash.empty
  }

  /**
   * Predefined test values for registered user.
   */
  trait RegisteredUserScope extends UserScope {
    val registeredUser = User
      .register(userId, email, password, verificationCode, instanceId, timestamp)
      .markCommitted
  }
}
