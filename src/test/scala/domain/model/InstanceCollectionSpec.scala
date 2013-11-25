package domain.model

import _root_.baseSpecifications.PersistenceSpecification
import io.multiverse.domain.model.common.commands.CommandConversions.headCommandToChain
import io.multiverse.domain.model.instance.collections.{InstanceReport, InstanceSet}
import io.multiverse.domain.model.instance.commands.{SignOut, SignIn, CreateInstance}
import io.multiverse.domain.model.instance.{InstanceId, Version}
import io.multiverse.domain.model.user.UserId
import io.multiverse.domain.stores.FileSystemStore
import java.util.UUID
import org.specs2.specification.Scope

/**
 * Instance model specification.
 */
class InstanceCollectionSpec extends PersistenceSpecification {
  "instance collection" should {
    "support sign-ins" in new InstanceScope {
      val instanceA = CreateInstance(version, instanceAId, timestamp) /> SignIn(userA, instanceAId, timestamp)

      verifyState(
        instanceA.changes,
        stores,
        InstanceSet.createIn,
        store => {
          val instanceReport = InstanceReport.getFor(store)
          instanceReport.getSignedInUsers must beEqualTo(List(userA))
          instanceReport.getSignedInUserCount must beEqualTo(1)
          instanceReport.getUserInstance(userA) must beEqualTo(instanceAId)
        }
      )
    }

    "support sign-ins to multiple instances from the same user" in new InstanceScope {
      val instanceA = CreateInstance(version, instanceAId, timestamp) /> SignIn(userA, instanceAId, timestamp)
      val instanceB = CreateInstance(version, instanceBId, timestamp) /> SignIn(userA, instanceBId, timestamp)

      verifyState(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceSet.createIn,
        store => {
          val instanceReport = InstanceReport.getFor(store)
          instanceReport.getSignedInUsers must beEqualTo(List(userA))
          instanceReport.getSignedInUserCount must beEqualTo(1)

          // The domain model should recognise a 0..1-1 mapping between users and instances, where the single instance
          // is the latest instance the user signed-into.
          instanceReport.getUserInstance(userA) must beEqualTo(instanceBId)
        }
      )
    }

    "support sign-ins from multiple users" in new InstanceScope {
      val instanceA = CreateInstance(version, instanceAId, timestamp) /> SignIn(userA, instanceAId, timestamp)
      val instanceB = CreateInstance(version, instanceBId, timestamp) /> SignIn(userB, instanceBId, timestamp)

      verifyState(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceSet.createIn,
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
      val instanceA = CreateInstance(version, instanceAId, timestamp) /> SignIn(userA, instanceAId, timestamp)
      val instanceB = CreateInstance(version, instanceBId, timestamp) /> SignIn(userB, instanceBId, timestamp) /> SignOut(instanceBId, timestamp)

      verifyState(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceSet.createIn,
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
    val userA = UserId(UUID.randomUUID)
    val userB = UserId(UUID.randomUUID)
    val version = Version(0)
    val stores = List(FileSystemStore.randomStore) // , GoogleAppEngineStore.randomStore
  }
}
