package com.wagerfield.multiverse.application

import com.wagerfield.multiverse.domain.shared.{EventToken, Event}
import scala.xml._
import java.util.UUID
import concurrent.Lock

trait EventLogComponent {
	def eventLog: EventLog

	/**
	 * Append-only store of aggregate-related events.
	 */
	trait EventLog {
		/**
		 * Filtered section of the event log.
		 * @param exclusiveFirstEvent This event and all those before it will be excluded.
		 * @param aggregateIdFilter Only events for these specific aggregates will be included.
		 * @param aggregateTypeFilter Only events for aggregates of these specific types will be included.
		 * @return
		 */
		def where(exclusiveFirstEvent: EventToken = null, aggregateIdFilter: List[UUID] = Nil, aggregateTypeFilter: List[String] = Nil): List[Event]

		/**
		 * Appends an event to the log.
		 * @param event Event to append.
		 * @param previousEvent Previous event for the given event's aggregate. Operation will fail if persistence store contains a different event.
		 * @return True if successful, other false if a conflict occurred.
		 */
		def tryAppend(event: Event, previousEvent: EventToken = null):Boolean

		/**
		 * Appends a sequence of events to the log.
		 * @param event Event to append.
		 * @param previousEvent Previous event for the given event's aggregate. Operation will fail if persistence store contains a different event.
		 * @return True if successful, other false if a conflict occurred.
		 */
		def tryAppendMany(event: List[Event], previousEvent: EventToken = null):Boolean
	}
}

trait MemoryEventLogComponent extends EventLogComponent {

	/**
	 * Append-only store of aggregate-related events.
	 */
	class MemoryEventLog extends EventLog {
		var events:List[Event] = Nil
		val lock:Lock = new Lock

		/**
		 * Filtered section of the event log.
		 * @param exclusiveFirstEvent This event and all those before it will be excluded.
		 * @param aggregateIdFilter Only events for these specific aggregates will be included.
		 * @param aggregateTypeFilter Only events for aggregates of these specific types will be included.
		 * @return
		 */
		def where(exclusiveFirstEvent: EventToken, aggregateIdFilter: List[UUID], aggregateTypeFilter: List[String] = Nil): List[Event] = {
			lock.acquire()
			try {
				events.filter(a => aggregateTypeFilter.contains(a.aggregateType) || aggregateIdFilter.contains(a.aggregateId))
			}
			finally {
				lock.release()
			}
		}

		/**
		 * Appends an event to the log.
		 * @param event Event to append.
		 * @param previousEvent Previous event for the given event's aggregate. Operation will fail if persistence store contains a different event.
		 * @return True if successful, other false if a conflict occurred.
		 */
		def tryAppend(event: Event, previousEvent: EventToken):Boolean = {
			lock.acquire()
			try {
				if (isConflict(previousEvent)) {
					false
				}
				else {
					events = event +: events
					true
				}
			}
			finally {
				lock.release()
			}
		}

		/**
		 * Appends a sequence of events to the log.
		 * @param event Event to append.
		 * @param previousEvent Previous event for the given event's aggregate. Operation will fail if persistence store contains a different event.
		 * @return True if successful, other false if a conflict occurred.
		 */
		def tryAppendMany(event: List[Event], previousEvent: EventToken):Boolean = {
			lock.acquire()
			try {
				if (isConflict(previousEvent)) {
					false
				}
				else {
					events = event ++ events
					true
				}
			}
			finally {
				lock.release()
			}
		}

		/**
		 * Tests whether the provided event conflicts with the current log.
		 * @param previousEvent The previous event known to the caller.
		 * @return True if conflicts, otherwise false.
		 */
		def isConflict(previousEvent: EventToken) = previousEvent == null && events != Nil || events == Nil || previousEvent != events.last.token
	}

}
