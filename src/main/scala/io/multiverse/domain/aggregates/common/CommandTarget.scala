package io.multiverse.domain.aggregates.common

/**
 * Produces output from received commands.
 * @tparam C Command type.
 */
trait CommandTarget[C] {
  /**
   * Convenience method for processing unhandled commands.
   * @param command Unhandled command.
   * @return Nothing (exception always thrown).
   */
  protected def unhandledCommand(command: C) = sys.error("Command " + command + " does not apply to " + this + ".")
}
