package com.wagerfield.multiverse.domain.model.instance

import com.wagerfield.multiverse.domain.model.species.SpeciesId
import com.wagerfield.multiverse.domain.shared.{ValidatedEntityAggregateFactory, AggregateRoot, Entity}

/**
 * Running instance of the application.
 * @param uncommittedEvents Events pending commitment.
 * @param id Unique instance ID.
 * @param isUserSignedIn Flag indicating if a user is signed-in to this instance.
 */
case class Instance private(uncommittedEvents: List[InstanceEvent],
                            id:InstanceId,
                            isUserSignedIn:Boolean)
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
    require(!isUserSignedIn, "Instances only support a single signed-in user.")
    applyEvent(UserSignedIn(id, timeStamp, user))
  }

  /**
   * Signs the current user out of this instance.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return Aggregate without a signed-in user.
   */
  def signOut(timeStamp:Long): Instance = {
    require(isUserSignedIn, "Signing-out requires the instance to have a signed-in user.")
    applyEvent((UserSignedOut(id, timeStamp)))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: InstanceEvent): Instance = {
    event match {
      case event:UserSignedIn => copy(uncommittedEvents = uncommittedEvents :+ event, isUserSignedIn = true)
      case event:UserSignedOut => copy(uncommittedEvents = uncommittedEvents :+ event, isUserSignedIn = false)
      case event: InstanceEvent => unhandled(event)
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
      case event: InstanceCreated => Instance(Nil :+ event, event.instanceId, false)
      case event: InstanceEvent => unhandled(event)
    }
  }
}
