package domain.model

import _root_.baseSpecifications.CommandCombinators.{chainToTestChain, headCommandToTestChain}
import io.multiverse.domain.model.common.commands.CommandCombinators.headCommandToChain
import io.multiverse.domain.model.common.commands.Commit
import io.multiverse.domain.model.common.values.{Hash, Email}
import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.commands.{RegisterUser, VerifyUserEmail, DeduplicateUser}
import io.multiverse.domain.model.user.{UserDeduplicated, UserEmailVerified, UserRegistered, UserId}
import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/**
 * User specification.
 */
class UserSpec extends Specification {
  "user" should {
    "be registered" in new UserScope {
      (RegisterUser(userId, email, password, verificationCode, instanceId, timestamp)
        yields UserRegistered(userId, email, password, verificationCode, instanceId, timestamp))
    }
  }

  "registered user" should {
    "verify their email address with a valid code" in new RegisteredUserScope {
      (registeredUser
        after VerifyUserEmail(userId, verificationCode, instanceId, timestamp)
        yields UserEmailVerified(userId, instanceId, timestamp))
    }

    "not verify their email address with an invalid code" in new RegisteredUserScope {
      val invalidCode = Hash(List.fill(Hash.size)(1.toByte))
      (registeredUser
        cannot VerifyUserEmail(userId, invalidCode, instanceId, timestamp)
        because VerifyUserEmail.VerificationCodeMatch)
    }

    "support de-duplication with a single canonical user" in new RegisteredUserScope {
      val canonicalUser = UserId(UUID.randomUUID)
      (registeredUser
        after DeduplicateUser(userId, canonicalUser, instanceId, timestamp)
        yields UserDeduplicated(userId, canonicalUser, instanceId, timestamp))
    }

    "support idempotent de-duplication with the same canonical user" in new DeduplicatedUserScope {
      (deduplicatedUser
        after DeduplicateUser(userId, canonicalUser, instanceId, timestamp)
        yields Nil)
    }

    "not support self-referential de-duplication" in new RegisteredUserScope {
      (DeduplicateUser(userId, userId, instanceId, timestamp)
        must throwA[Exception])
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
    val registeredUser = RegisterUser(userId, email, password, verificationCode, instanceId, timestamp) after Commit()
  }

  /**
   * Predefined test values for deduplicated user.
   */
  trait DeduplicatedUserScope extends RegisteredUserScope {
    val canonicalUser = UserId(UUID.randomUUID)
    val deduplicatedUser = (registeredUser
      after DeduplicateUser(userId, canonicalUser, instanceId, timestamp)
      after Commit())
  }
}
