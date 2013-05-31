package domain.collections

import io.multiverse.domain.collections.user.{UserReport, UserCompensationStrategy, DuplicateEmail, UserConflict, UserSet}
import io.multiverse.domain.aggregates.instance.InstanceId
import io.multiverse.domain.aggregates.user.{User, UserId}
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
      val userA = User.register(userIdA, userEmailA, password, verificationCode, instanceId, timestamp)
      val userB = User.register(userIdB, userEmailB, password, verificationCode, instanceId, timestamp)
      val userC = User.register(userIdC, userEmailC, password, verificationCode, instanceId, timestamp)

      applyState(
        userA.changes ++ userB.changes ++ userC.changes,
        stores,
        UserSet.createIn(_)
      )
    }

    "track email availability" in new UserScope {
      val userA = User.register(userIdA, userEmailA, password, verificationCode, instanceId, timestamp)
      val userB = User.register(userIdB, userEmailB, password, verificationCode, instanceId, timestamp)

      verifyState(
        userA.changes ++ userB.changes,
        stores,
        UserSet.createIn(_),
        store => {
          val userReport = UserReport.getFor(store)
          userReport.isEmailTaken(userEmailA) must beEqualTo(true)
          userReport.isEmailTaken(userEmailB) must beEqualTo(true)
          userReport.isEmailTaken(userEmailC) must beEqualTo(false)
        }
      )
    }

    "compensate duplicate emails" in new UserScope {
      val userA = User.register(userIdA, userEmailA, password, verificationCode, instanceId, timestamp)
      val userB = User.register(userIdB, userEmailA, password, verificationCode, instanceId, timestamp)
      val userC = User.register(userIdC, userEmailA, password, verificationCode, instanceId, timestamp)

      verifyConflicts(
        userA.changes ++ userB.changes ++ userC.changes,
        List[UserConflict](DuplicateEmail(userIdB), DuplicateEmail(userIdC)),
        stores,
        UserSet.createIn(_),
        UserCompensationStrategy.instance
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
