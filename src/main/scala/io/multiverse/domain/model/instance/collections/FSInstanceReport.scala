package io.multiverse.domain.model.instance.collections

import io.multiverse.domain.model.instance.InstanceId
import io.multiverse.domain.model.user.UserId
import io.multiverse.domain.stores.FileSystemStore
import java.io.File
import java.util.UUID
import scala.io.Source

/**
 * Query interface for instance models hosted on the file system.
 */
class FSInstanceReport(val store: FileSystemStore) extends InstanceReport with FSInstancePaths {
  /**
   * Gets all users who are currently signed-in.
   * @return Signed-in users.
   */
  def getSignedInUsers: List[UserId] = {
    new File(signedInUsersPath).list.map(f => UserId(UUID.fromString(f))).toList
  }

  /**
   * Gets the count of users who are currently signed-in.
   * @return Number of signed-in users.
   */
  def getSignedInUserCount: Int = {
    new File(signedInUsersPath).list.length
  }

  /**
   * Gets the instance the specified user is operating within.
   * @param user Signed-in user.
   * @return Instance the specified user is signed-into.
   */
  def getUserInstance(user: UserId): InstanceId = {
    val idSize = 36
    val userFile = signedInUsersPath + user.value
    val uuid = Source.fromFile(userFile).take(idSize).mkString
    InstanceId(UUID.fromString(uuid))
  }
}
