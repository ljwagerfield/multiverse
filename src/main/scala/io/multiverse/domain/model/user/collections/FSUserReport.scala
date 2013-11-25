package io.multiverse.domain.model.user.collections

import io.multiverse.domain.stores.FileSystemStore
import java.io.File
import org.apache.commons.codec.binary.Base64
import io.multiverse.domain.model.common.values.Email

/**
 * Query interface for user models hosted on the file system.
 */
class FSUserReport(val store: FileSystemStore) extends UserReport with FSUserPaths {
  /**
   * Tests the availability of the provided email.
   * @param email Email to check.
   * @return True if email taken. False if available.
   */
  def isEmailTaken(email: Email): Boolean = {
    // Encode email to protect against invalid path characters. Possible if name portion of email address is quoted (see RFC 822).
    new File(emailsPath + Base64.encodeBase64(email.value.getBytes).mkString).exists
  }
}
