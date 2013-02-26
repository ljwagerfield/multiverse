package com.wagerfield.multiverse.domain.shared

import java.util.UUID

/**
 * Uniquely identifies an event. Events are value objects, hence the term 'token'.
 * @param value Unique event token.
 */
case class EventToken(value: UUID)
