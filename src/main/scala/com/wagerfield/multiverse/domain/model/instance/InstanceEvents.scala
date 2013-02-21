package com.wagerfield.multiverse.domain.model.instance

import com.wagerfield.multiverse.domain.model.species.SpeciesId

/**
 * Instance created: version upgrades require instance to be abandoned and new one created; instances include clients, web services and web processes.
 */
case class InstanceCreated(instanceId: InstanceId, version:Version) extends InstanceEvent

/**
 * User signed in: certain commands only valid if current instance has a signed-in user.
 */
case class UserSignedIn(instanceId: InstanceId, speciesId:SpeciesId) extends InstanceEvent

/**
 * User signed in: AI is enabled for species when not in user mode.
 */
case class UserSignedOut(instanceId: InstanceId) extends InstanceEvent