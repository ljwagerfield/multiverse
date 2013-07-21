package domain.collections

import io.multiverse.domain.collections.user.{UserReport, UserCompensationStrategy, DuplicateEmail, UserConflict, UserSet}
import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.user.{DeduplicateUser, RegisterUser, User, UserId}
import java.util.UUID
import org.specs2.specification.Scope
import common.PersistenceSpecification
import io.multiverse.domain.stores.FileSystemStore
import io.multiverse.domain.aggregates.common.{Hash, Email}

/**
 * User model specification.
 */
class UserSpec extends PersistenceSpecification {
  "user collection" should {
    "support registrations" in new UserScope {
      val userA = RegisterUser(userIdA, userEmailA, password, verificationCode, instanceId, timestamp).evaluation
      val userB = RegisterUser(userIdB, userEmailB, password, verificationCode, instanceId, timestamp).evaluation
      val userC = RegisterUser(userIdC, userEmailC, password, verificationCode, instanceId, timestamp).evaluation

      applyState(
        userA ++ userB ++ userC,
        stores,
        UserSet.createIn
      )
    }

    "track email availability" in new UserScope {
      val userA = RegisterUser(userIdA, userEmailA, password, verificationCode, instanceId, timestamp).evaluation
      val userB = RegisterUser(userIdB, userEmailB, password, verificationCode, instanceId, timestamp).evaluation

      verifyState(
        userA ++ userB,
        stores,
        UserSet.createIn,
        store => {
          val userReport = UserReport.getFor(store)
          userReport isEmailTaken userEmailA must beTrue
          userReport isEmailTaken userEmailB must beTrue
          userReport isEmailTaken userEmailC must beFalse
        }
      )
    }

    "compensate duplicate emails" in new UserScope {
      val userA = RegisterUser(userIdA, userEmailA, password, verificationCode, instanceId, timestamp).evaluation
      val userB = RegisterUser(userIdB, userEmailA, password, verificationCode, instanceId, timestamp).evaluation
      val userC = RegisterUser(userIdC, userEmailA, password, verificationCode, instanceId, timestamp).evaluation

      verifyCompensation(
        userA ++ userB ++ userC,
        stores,
        UserSet.createIn,
        UserCompensationStrategy.instance,
        List[UserConflict](DuplicateEmail(userIdB), DuplicateEmail(userIdC)),
        List(DeduplicateUser(userIdB, userIdA, instanceId, timestamp), DeduplicateUser(userIdC, userIdA, instanceId, timestamp))
      )
    }
  }

  /**
   * Predefined test values for users.
   */
  trait UserScope extends Scope {
    val timestamp = 0
    val instanceId = InstanceId(UUID.randomUUID)
    val stores = List(FileSystemStore.randomStore) // , GoogleAppEngineStore.randomStore
    val userIdA = UserId(UUID.randomUUID)
    val userIdB = UserId(UUID.randomUUID)
    val userIdC = UserId(UUID.randomUUID)
    val userEmailA = Email("lawrence@wagerfield.com")
    val userEmailB = Email("matthew@wagerfield.com")
    val userEmailC = Email("paulj_85@yahoo.co.uk")
    val verificationCode = Hash.empty
    val password = Hash.empty
  }
}
