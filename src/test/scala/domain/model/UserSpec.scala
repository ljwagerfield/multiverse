package domain.model

import _root_.baseSpecifications.CommandSpecification

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.commands.CommandConversions.{aggregateRootToChain, headCommandToChain, chainToAggregateRoot}
import io.multiverse.domain.model.user.{User, UserEvent, UserDeduplicated, UserEmailVerified, UserRegistered, UserId}
import java.util.UUID
import org.specs2.specification.Scope
import io.multiverse.domain.model.user.commands.{RegisterUser, VerifyUserEmail, DeduplicateUser}
import io.multiverse.domain.model.common.values.{Hash, Email}

/**
 * User specification.
 */
class UserSpec extends CommandSpecification {
  "user" should {
    "be registered" in new UserScope {
      assertSuccess[User, UserEvent, RegisterUser](
        RegisterUser(userId, email, password, verificationCode, instanceId, timestamp),
        List(UserRegistered(userId, email, password, verificationCode, instanceId, timestamp)))
    }
  }

  "registered user" should {
    "verify their email address with a valid code" in new RegisteredUserScope {
      assertSuccess[User, UserEvent, VerifyUserEmail](
        registeredUser,
        VerifyUserEmail(userId, verificationCode, instanceId, timestamp),
        List(UserEmailVerified(userId, instanceId, timestamp)))
    }

    "not verify their email address with an invalid code" in new RegisteredUserScope {
      val invalidCode = Hash(List.fill(Hash.size)(1.toByte))
      assertFailure[User, UserEvent, VerifyUserEmail](
        registeredUser,
        VerifyUserEmail(userId, invalidCode, instanceId, timestamp),
        c => List(VerifyUserEmail.VerificationCodeMatch(c)))
    }

    "support de-duplication with a single canonical user" in new RegisteredUserScope {
      val canonicalUser = UserId(UUID.randomUUID)
      assertUnconditional[User, UserEvent, DeduplicateUser](
        registeredUser,
        DeduplicateUser(userId, canonicalUser, instanceId, timestamp),
        List(UserDeduplicated(userId, canonicalUser, instanceId, timestamp)))
    }

    "support idempotent de-duplication with the same canonical user" in new DeduplicatedUserScope {
      assertIdempotent[User, UserEvent, DeduplicateUser](
        deduplicatedUser,
        DeduplicateUser(userId, canonicalUser, instanceId, timestamp))
    }

    "not support self-referential de-duplication" in new RegisteredUserScope {
      DeduplicateUser(userId, userId, instanceId, timestamp) must throwA[Exception]
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
    val registeredUser = headCommandToChain(RegisterUser(userId, email, password, verificationCode, instanceId, timestamp)).markCommitted
  }

  /**
   * Predefined test values for deduplicated user.
   */
  trait DeduplicatedUserScope extends RegisteredUserScope {
    val canonicalUser = UserId(UUID.randomUUID)
    val deduplicatedUser = (aggregateRootToChain(registeredUser) /> DeduplicateUser(userId, canonicalUser, instanceId, timestamp)).markCommitted
  }
}
