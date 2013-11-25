package io.multiverse.domain.model.user.collections

import io.multiverse.domain.stores.FileSystemStore
import java.io.File

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
