package com.wagerfield.multiverse.domain.shared

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Loads aggregates from given histories. The resulting object collections are composed by a single root instance
 * of [[com.wagerfield.multiverse.domain.shared.AggregateRoot]].
 */
trait AggregateFactory[AR <: AggregateRoot[AR, Event], Event] extends EventSourced[AR, Event] {
	/**
	 * Loads an aggregate from a history represented by a sequence of events.
	 * @param history Sequence of events representing a history to form the aggregate.
	 * @return Aggregate denormalized from the sequence of events.
	 */
	def loadFromHistory(history: Iterable[Event]): AR = {
		history.foldLeft(applyEvent(history.head))(_.applyEvent(_)).markCommitted
	}
}