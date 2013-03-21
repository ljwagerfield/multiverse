package com.wagerfield.multiverse.application

import java.util.UUID

/**
 * Object that is not defined by its attributes, but rather by a thread of continuity and its identity.
 */
trait Event {
  val token: EventToken
  val aggregateId: UUID
  val aggregateType: String
}
