package com.wagerfield.multiverse.domain.model.system

import com.wagerfield.multiverse.domain.shared.{Entity, ValidatedValueObjectAggregateFactory, AggregateRoot}
import com.wagerfield.multiverse.domain.model.instance.InstanceId

/**
 * Entire logical system containing all state and instances.
 * @param uncommittedEvents Events pending commitment.
 * @param id Unique system ID.
 */
case class System private(uncommittedEvents: List[SystemEvent], id:SystemId)
  extends Entity[SystemId] with AggregateRoot[System, SystemEvent]
{
  /**
   * Processes uncommitted events.
   * @return Aggregate with no uncommitted events.
   */
  def markCommitted: System = copy(uncommittedEvents = Nil)

  /**
   * Pauses the game.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @param message Message for users explaining why the game has been paused.
   * @return System with game paused.
   */
  def pauseGame(message:String, instanceId:InstanceId, timeStamp:Long): System = {
    applyEvent(GamePaused(instanceId, timeStamp, id, message))
  }

  /**
   * Resumes the game.
   * @param instanceId Instance the event occurred in.
   * @param timeStamp Milliseconds elapsed since midnight 1970-01-01 UTC.
   * @return System with game paused.
   */
  def resumeGame(instanceId:InstanceId, timeStamp:Long): System = {
    applyEvent(GameResumed(instanceId, timeStamp, id))
  }

  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def applyEvent(event: SystemEvent): System = {
    event match {
      case event:GamePaused => copy(uncommittedEvents = uncommittedEvents :+ event)
      case event:GameResumed => copy(uncommittedEvents = uncommittedEvents :+ event)
      case event:SystemEvent => unhandled(event)
    }
  }
}

/**
 * Planet industry factory.
 */
object System extends ValidatedValueObjectAggregateFactory[System, SystemEvent] {
  /**
   * Initializes the system.
   * @param systemId Unique system ID.
   * @return Aggregate representing the initialized system.
   */
  def init(systemId:SystemId):System =
    System(Nil, systemId)

  /**
   * Gets the prototypical instance of an aggregate using the initial event from its history.
   * @param initialEvent Initial event in the aggregate's history.
   * @return
   */
  def getPrototype(initialEvent: SystemEvent): System = init(initialEvent.systemId)
}
