package com.wagerfield.multiverse.application

import com.wagerfield.multiverse.domain.shared.{EventToken, Event}
import scala.xml._
import java.util.UUID
import com.wagerfield.multiverse.domain.model.solarSystem.SolarSystem

trait SolarSystemServiceComponent {
	def solarSystemService: SolarSystemService

	/**
	 * Append-only store of aggregate-related events.
	 */
	trait SolarSystemService {
		/**
		 * Returns the store's inconsistent solar systems with compensation events applied.
		 * @param store
		 * @return
		 */
		def getCompensatedSolarSystems(store: String): List[SolarSystem]
	}
}

trait GoogleHrdSolarSystemServiceComponent extends SolarSystemServiceComponent {

	/**
	 * Append-only store of aggregate-related events.
	 */
	class MemorySolarSystemService extends SolarSystemService {
		/**
		 * Filtered section of the event log.
		 * @param exclusiveFirstEvent This event and all those before it will be excluded.
		 * @param aggregateIdFilter Only events for these specific aggregates will be included.
		 * @param aggregateTypeFilter Only events for aggregates of these specific types will be included.
		 * @return
		 */
		def where(exclusiveFirstEvent: EventToken, aggregateIdFilter: List[UUID], aggregateTypeFilter: List[String] = Nil): List[Event] = {
			(XML.loadFile("SolarSystemService.xml") \ "event").filter(_ => aggregateTypeFilter.contains(_ \ "@aggregateType") || aggregateIdFilter.contains(_ \ "@aggregateId")).map(serializationStrategy.deserialize(new Blob(_ \ "@content")))
		}

		/**
		 * Appends an event to the log.
		 * @param event Event to append.
		 * @param previousAggregateEvent Previous event for the given event's aggregate. Operation will fail if persistence store contains a different event.
		 */
		def append(event: Event, previousAggregateEvent: EventToken) {
			val stream = new FileStream("SolarSystemService.xml")
			try {
				val lock = stream.getChannel().getLock
				val xmlFile = XML.load(stream)
				xmlFile.addChild(<event></event>)

			}
			finally {
				stream.close
			}
		}

		/**
		 * Appends a sequence of events to the log.
		 * @param event Event to append.
		 * @param previousAggregateEvent Previous event for the given event's aggregate. Operation will fail if persistence store contains a different event.
		 */
		def append(event: List[Event], previousAggregateEvent: EventToken) {}
	}

}
