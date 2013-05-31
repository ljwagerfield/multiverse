package domain.aggregates

import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.user.{UserDeduplicated, UserEmailVerified, UserRegistered, UserId, User}
import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import io.multiverse.domain.aggregates.common.{Email, Hash}

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

    "support de-duplication with a single canonical user" in new RegisteredUserScope {
      val canonicalUser = UserId(UUID.randomUUID)
      registeredUser
        .deduplicate(canonicalUser, instanceId, timestamp)
        .changes must beEqualTo(List(
          UserDeduplicated(instanceId, timestamp, userId, canonicalUser)
      ))
    }

    "support idempotent de-duplication with the same canonical user" in new RegisteredUserScope {
      val canonicalUser = UserId(UUID.randomUUID)
      registeredUser
        .deduplicate(canonicalUser, instanceId, timestamp)
        .markCommitted
        .deduplicate(canonicalUser, instanceId, timestamp)
        .changes must beEmpty
    }

    "not support self-referential de-duplication" in new RegisteredUserScope {
      val invalidCode = Hash(List.fill(Hash.size)(1.toByte))
      registeredUser
        .deduplicate(userId, instanceId, timestamp) must throwA[Exception]
    }

    "not support de-duplication with multiple canonical users" in new RegisteredUserScope {
      val canonicalUser = UserId(UUID.randomUUID)
      val canonicalUser2 = UserId(UUID.randomUUID)
      registeredUser
        .deduplicate(canonicalUser, instanceId, timestamp)
        .deduplicate(canonicalUser2, instanceId, timestamp) must throwA[Exception]
      registeredUser
        .deduplicate(canonicalUser, instanceId, timestamp)
        .deduplicate(userId, instanceId, timestamp) must throwA[Exception]
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
