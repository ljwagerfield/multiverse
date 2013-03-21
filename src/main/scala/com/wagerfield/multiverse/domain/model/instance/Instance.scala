package com.wagerfield.multiverse.domain.model.instance

import com.wagerfield.multiverse.domain.model.species.SpeciesId
import com.wagerfield.multiverse.domain.shared.{ValidatedEntityAggregateFactory, AggregateRoot, Entity}

/**
 * Running instance of the application.
 * @param uncommittedEvents Events pending commitment.
 * @param id Unique instance ID.
 * @param version Application version running this instance.
 * @param signedInUser The signed-in user this instance represents.
 */
case class Instance private(uncommittedEvents: List[InstanceEvent],
                            id:InstanceId,
                            version:Version,
                            signedInUser:Option[SpeciesId])
  extends Entity[InstanceId] with AggregateRoot[Instance, InstanceEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: Instance = copy(uncommittedEvents = Nil)

  /**
   * Signs a user into this instance.
   * @param user User to be signed-in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate with signed-in user.
   */
  def signIn(user:SpeciesId, timeStamp:Long): Instance = {
    // TODO: Validate no-one is signed-in.
    applyEvent(UserSignedIn(id, timeStamp, user))
  }

  /**
   * Signs the current user out of this instance.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate without a signed-in user.
   */
  def signOut(timeStamp:Long): Instance = {
    // TODO: Validate a user is signed-in.
    applyEvent((UserSignedOut(id, timeStamp)))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: InstanceEvent): Instance = {
    event match {
      case event:UserSignedIn => copy(uncommittedEvents = uncommittedEvents :+ event, signedInUser = Some(event.speciesId))
      case event:UserSignedOut => copy(uncommittedEvents = uncommittedEvents :+ event, signedInUser = None)
    }
  }
}

/**
 * Instance factory.
 */
object Instance extends ValidatedEntityAggregateFactory[Instance, InstanceEvent] {
  /**
   * Creates a new instance.
   * @param instanceId Unique ID for the new instance.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @param version Application version number.
   * @return Aggregate representing new instance.
   */
  def create(instanceId:InstanceId, timeStamp:Long, version:Version):Instance =
    applyEvent(InstanceCreated(instanceId, timeStamp, version:Version))

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: InstanceEvent): Instance = {
    event match {
      case event: InstanceCreated => Instance(Nil :+ event, event.instanceId, event.version, None)
      case event: InstanceEvent => unhandled(event)
    }
  }
}
