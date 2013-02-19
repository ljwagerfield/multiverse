package com.wagerfield.multiverse.domain.shared

/**
 * Copyright (c) Wagerfield Ltd.
 *
 * Object with event sourced state.
 */
trait EventSourced[ES <: EventSourced[ES, Event], Event] {
	/**
	 * Applies the given event as the head of the returned object's state.
	 * @param event Event representing new head state.
	 * @return
	 */
	def applyEvent(event: Event): ES

	/**
	 * Convenience method for processing unhandled events.
	 * @param event Unhandled event.
	 * @return Nothing (exception always thrown).
	 */
	protected def unhandled(event: Event) = sys.error("Event " + event + " does not apply to " + this)
}
