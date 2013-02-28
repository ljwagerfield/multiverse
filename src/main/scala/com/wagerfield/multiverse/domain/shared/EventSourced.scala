package com.wagerfield.multiverse.domain.shared

/**
 * Object with event sourced state.
 * @tparam T The derived class type.
 * @tparam E The event type.
 */
trait EventSourced[T <: EventSourced[T, E], E] {
  /**
   * Applies the given event as the head of the returned object's state.
   * @param event E representing new head state.
   * @return
   */
  def applyEvent(event: E): T

  /**
   * Convenience method for processing unhandled events.
   * @param event Unhandled event.
   * @return Nothing (exception always thrown).
   */
  protected def unhandled(event: E) = sys.error("Event " + event + " does not apply to " + this)
}
