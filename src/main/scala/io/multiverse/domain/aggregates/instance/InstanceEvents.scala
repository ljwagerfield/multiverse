package io.multiverse.domain.aggregates.instance

import io.multiverse.domain.aggregates.user.UserId

/**
 * Instance created: instances include clients, web services and web processes.
 * @param instanceId Unique ID for this instance.
 * @param version The application version number. Upgrades to this number require the instance to be abandoned and new one created.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class InstanceCreated(instanceId:InstanceId, version:Version, timestamp:Long) extends InstanceEvent

/**
 * User signed in: certain commands only valid if current instance has a signed-in user.
 * @param instanceId Instance the signed-in user is running within. Instances can have 0..1 signed-in users.
 * @param userId The user's species ID.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class UserSignedIn(instanceId:InstanceId, userId:UserId, timestamp:Long) extends InstanceEvent

/**
 * User signed in: AI is enabled for species when not in user mode.
 * @param instanceId The instance the user was signed-in to.
 * @param timestamp Milliseconds elapsed since midnight 1970-01-01 UTC.
 */
case class UserSignedOut(instanceId:InstanceId, timestamp:Long) extends InstanceEvent
