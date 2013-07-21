package io.multiverse.domain.aggregates.common

/**
 * Object with event sourced state.
 * @tparam T The derived class type.
 * @tparam E The event type.
 */
trait EventSourced[T <: EventSourced[T, E], E] {
  /**
   * Applies the given event as the head of the returned aggregate's state.
   * @param event Event representing new head state.
   * @return Aggregate with event appended and new state applied.
   */
  def apply(event: E): T

  /**
   * Convenience method for processing unhandled events.
   * @param event Unhandled event.
   * @return Nothing (exception always thrown).
   */
  protected def unhandledEvent(event: E) = sys.error("Event " + event + " does not apply to " + this + ".")
}
