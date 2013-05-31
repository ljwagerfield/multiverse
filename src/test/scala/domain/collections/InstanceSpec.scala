package domain.collections

import common.PersistenceSpecification
import io.multiverse.domain.aggregates.instance.{InstanceId, Version, Instance}
import io.multiverse.domain.aggregates.user.UserId
import io.multiverse.domain.collections.instance.{InstanceReport, InstanceSet}
import io.multiverse.domain.stores.FileSystemStore
import java.util.UUID
import org.specs2.specification.Scope

/**
 * Instance model specification.
 */
class InstanceSpec extends PersistenceSpecification {
  "instance collection" should {
    "support sign-ins" in new InstanceScope {
      val user = UserId(UUID.randomUUID)
      val instance = Instance.create(instanceAId, timestamp, version).signIn(user, timestamp)

      verifyState(
        instance.changes,
        stores,
        InstanceSet.createIn(_),
        store => {
          val instanceReport = InstanceReport.getFor(store)
          instanceReport.getSignedInUsers must beEqualTo(List(user))
          instanceReport.getSignedInUserCount must beEqualTo(1)
          instanceReport.getUserInstance(user) must beEqualTo(instanceAId)
        }
      )
    }

    "support sign-ins to multiple instances from the same user" in new InstanceScope {
      val user = UserId(UUID.randomUUID)
      val instanceA = Instance.create(instanceAId, timestamp, version).signIn(user, timestamp)
      val instanceB = Instance.create(instanceBId, timestamp, version).signIn(user, timestamp)

      verifyState(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceSet.createIn(_),
        store => {
          val instanceReport = InstanceReport.getFor(store)
          instanceReport.getSignedInUsers must beEqualTo(List(user))
          instanceReport.getSignedInUserCount must beEqualTo(1)

          // The domain model should recognise a 0..1-1 mapping between users and instances, where the single instance
          // is the latest instance the user signed-into.
          instanceReport.getUserInstance(user) must beEqualTo(instanceBId)
        }
      )
    }

    "support sign-ins from multiple users" in new InstanceScope {
      val userA = UserId(UUID.randomUUID)
      val userB = UserId(UUID.randomUUID)
      val instanceA = Instance.create(instanceAId, timestamp, version).signIn(userA, timestamp)
      val instanceB = Instance.create(instanceBId, timestamp, version).signIn(userB, timestamp)

      verifyState(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceSet.createIn(_),
        store => {
          val instanceReport = InstanceReport.getFor(store)
          instanceReport.getSignedInUsers must contain(userA, userB)
          instanceReport.getSignedInUserCount must beEqualTo(2)
          instanceReport.getUserInstance(userA) must beEqualTo(instanceAId)
          instanceReport.getUserInstance(userB) must beEqualTo(instanceBId)
        }
      )
    }

    "support sign-outs" in new InstanceScope {
      val userA = UserId(UUID.randomUUID)
      val userB = UserId(UUID.randomUUID)
      val instanceA = Instance.create(instanceAId, timestamp, version).signIn(userA, timestamp)
      val instanceB = Instance.create(instanceBId, timestamp, version).signIn(userB, timestamp).signOut(timestamp)

      verifyState(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceSet.createIn(_),
        store => {
          val instanceReport = InstanceReport.getFor(store)
          instanceReport.getSignedInUsers must beEqualTo(List(userA))
          instanceReport.getSignedInUserCount must beEqualTo(1)
          instanceReport.getUserInstance(userA) must beEqualTo(instanceAId)
        }
      )
    }
  }

  /**
   * Predefined test values for instance.
   */
  trait InstanceScope extends Scope {
    val timestamp = 0
    val instanceAId = InstanceId(UUID.randomUUID)
    val instanceBId = InstanceId(UUID.randomUUID)
    val version = Version(0)
    val stores = List(FileSystemStore.randomStore) // , GoogleAppEngineStore.randomStore
  }
}
