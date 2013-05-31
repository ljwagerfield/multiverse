package io.multiverse.domain.collections.user

import java.io.File
import io.multiverse.domain.stores.FileSystemStore

/**
 * File system paths relevant to the user model.
 */
trait FSUserPaths {
  /**
   * Identifies where this model is hosted.
   * @return Model location.
   */
  val store: FileSystemStore

  /**
   * Identifies where the email directory is located.
   * @return Emails path.
   */
  def emailsPath = store.path + File.separator + "emails" + File.separator

  /**
   * Identifies where the email directory is located.
   * @return Emails path.
   */
  def duplicateEmailConflictsPath = store.path + File.separator + "emailConflicts" + File.separator
}
