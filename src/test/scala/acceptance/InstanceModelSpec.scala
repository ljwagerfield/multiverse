package acceptance

import io.multiverse.application.persistence.{InstanceQueries, FileSystemStore, InstanceModel}
import io.multiverse.domain.model.instance.{InstanceId, Version, Instance}
import org.specs2.specification.Scope
import java.util.UUID
import io.multiverse.domain.model.species.SpeciesId

/**
 * Instance model specification.
 */
class InstanceModelSpec extends PersistenceModelSpecification {
  "instance model" should {
    "support sign-ins" in new InstanceScope {
      val user = SpeciesId(UUID.randomUUID)
      val instance = Instance.create(instanceAId, timestamp, version).signIn(user, timestamp)

      verifyChanges(
        instance.changes,
        stores,
        InstanceModel.createIn(_),
        store => {
          val queries = InstanceQueries.getFor(store)
          queries.getSignedInUsers must contain(user)
          queries.getSignedInUserCount must beEqualTo(1)
          queries.getUserInstance(user) must beEqualTo(instanceAId)
        }
      )
    }

    "support sign-ins to multiple instances from the same user" in new InstanceScope {
      val user = SpeciesId(UUID.randomUUID)
      val instanceA = Instance.create(instanceAId, timestamp, version).signIn(user, timestamp)
      val instanceB = Instance.create(instanceBId, timestamp, version).signIn(user, timestamp)

      verifyChanges(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceModel.createIn(_),
        store => {
          val queries = InstanceQueries.getFor(store)
          queries.getSignedInUsers must contain(user)
          queries.getSignedInUserCount must beEqualTo(1)

          // The domain model should recognise a 0..1-1 mapping between users and instances, where the single instance
          // is the latest instance the user signed-into.
          queries.getUserInstance(user) must beEqualTo(instanceBId)
        }
      )
    }

    "support sign-ins from multiple users" in new InstanceScope {
      val userA = SpeciesId(UUID.randomUUID)
      val userB = SpeciesId(UUID.randomUUID)
      val instanceA = Instance.create(instanceAId, timestamp, version).signIn(userA, timestamp)
      val instanceB = Instance.create(instanceBId, timestamp, version).signIn(userB, timestamp)

      verifyChanges(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceModel.createIn(_),
        store => {
          val queries = InstanceQueries.getFor(store)
          queries.getSignedInUsers must contain(userA, userB)
          queries.getSignedInUserCount must beEqualTo(2)
          queries.getUserInstance(userA) must beEqualTo(instanceAId)
          queries.getUserInstance(userB) must beEqualTo(instanceBId)
        }
      )
    }

    "support sign-outs" in new InstanceScope {
      val userA = SpeciesId(UUID.randomUUID)
      val userB = SpeciesId(UUID.randomUUID)
      val instanceA = Instance.create(instanceAId, timestamp, version).signIn(userA, timestamp)
      val instanceB = Instance.create(instanceBId, timestamp, version).signIn(userB, timestamp).signOut(timestamp)

      verifyChanges(
        instanceA.changes ++ instanceB.changes,
        stores,
        InstanceModel.createIn(_),
        store => {
          val queries = InstanceQueries.getFor(store)
          queries.getSignedInUsers must contain(userA)
          queries.getSignedInUserCount must beEqualTo(1)
          queries.getUserInstance(userA) must beEqualTo(instanceAId)
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
