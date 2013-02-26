package com.wagerfield.multiverse.domain.model.instance

import com.wagerfield.multiverse.domain.model.species.SpeciesId

/**
 * Instance created: instances include clients, web services and web processes.
 * @param instanceId Unique ID for this instance.
 * @param version The application version number. Upgrades to this number require the instance to be abandoned and new one created.
 */
case class InstanceCreated(instanceId: InstanceId, version:Version) extends InstanceEvent

/**
 * User signed in: certain commands only valid if current instance has a signed-in user.
 * @param instanceId Instance the signed-in user is running within. Instances can have 0..1 signed-in users.
 * @param speciesId The user's species ID.
 */
case class UserSignedIn(instanceId: InstanceId, speciesId:SpeciesId) extends InstanceEvent

/**
 * User signed in: AI is enabled for species when not in user mode.
 * @param instanceId The instance the user was signed-in to.
 */
case class UserSignedOut(instanceId: InstanceId) extends InstanceEvent