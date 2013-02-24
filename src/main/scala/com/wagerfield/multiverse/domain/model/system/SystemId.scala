package com.wagerfield.multiverse.domain.model.system

import java.util.UUID

/**
 * Uniquely identifies the system resource. System is a singleton under normal use.
 */
case class SystemId(id: UUID)