package io.multiverse.domain.model.system

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.common.{ImplicitAggregateFactory, AggregateRoot, Entity}

/**
 * Entire logical system containing all state and instances.
 * @param changes Events pending commitment.
 * @param id Unique system ID.
 */
case class System private(changes: List[SystemEvent], id:SystemId, isPaused:Boolean)
  extends Entity[SystemId] with AggregateRoot[System, SystemEvent] {

  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: System = copy(changes = Nil)

  /**
   * Pauses the game.
   * @param instanceId Instance the event occurred in.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @param message Message for users explaining why the game has been paused.
   * @return System with game paused.
   */
  def pauseGame(message:String, instanceId:InstanceId, timestamp:Long): System =
    apply(GamePaused(id, message, instanceId, timestamp))

  /**
   * Resumes the game.
   * @param instanceId Instance the event occurred in.
   * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return System with game paused.
   */
  def resumeGame(instanceId:InstanceId, timestamp:Long): System = {
    apply(GameResumed(id, instanceId, timestamp))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def apply(event: SystemEvent): System = {
    event match {
      case event:GamePaused => copy(changes = changes :+ event, isPaused = true)
      case event:GameResumed => copy(changes = changes :+ event, isPaused = false)
      case event:SystemEvent => unhandledEvent(event)
    }
  }
}

/**
 * System factory.
 */
object System extends ImplicitAggregateFactory[System, SystemEvent] {
  /**
   * Initializes the system.
   * @param systemId Unique system ID.
   * @return Aggregate representing the initialized system.
   */
  def init(systemId:SystemId):System =
    System(Nil, systemId, isPaused = false)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent: SystemEvent): System = init(initialEvent.systemId)
}
