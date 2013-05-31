package io.multiverse.domain.collections.instance

import java.io.File
import io.multiverse.domain.stores.FileSystemStore

/**
 * File system paths relevant to the instance model.
 */
trait FSInstancePaths {
  /**
   * Identifies where this model is hosted.
   */
  val store: FileSystemStore

  /**
   * Identifies where the directory of signed-in users is located.
   * @return Signed-in users path.
   */
  def signedInUsersPath = store.path + File.separator + "signedInUsers" + File.separator

  /**
   * Identifies where the directory of signed-in users is located.
   * @return Signed-in instances path.
   */
  def signedInInstancesPath = store.path + File.separator + "signedInInstances" + File.separator
}
