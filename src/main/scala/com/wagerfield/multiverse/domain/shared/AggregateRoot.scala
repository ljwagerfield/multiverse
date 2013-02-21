package com.wagerfield.multiverse.domain.shared

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Root object marshaling mutations to the coherent collection of all module objects (i.e. the aggregate).
 */
trait AggregateRoot[AR <: AggregateRoot[AR, Event], Event] extends EventSourced[AR, Event] {
	/**
	 * Events which are pending commitment into the aggregate.
	 * @return Sequence of uncommitted events.
	 */
	def uncommittedEvents: List[Event]

	/**
	 * Processes uncommitted events.
	 * @return Aggregate with no uncommitted events.
	 */
	def markCommitted: AR
}