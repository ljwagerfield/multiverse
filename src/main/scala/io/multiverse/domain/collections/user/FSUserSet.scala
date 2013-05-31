package io.multiverse.domain.collections.user

import io.multiverse.domain.aggregates.user.{UserId, UserRegistered, UserEvent}
import java.io.File
import org.apache.commons.codec.binary.Base64
import java.util.UUID
import io.multiverse.domain.stores.FileSystemStore

/**
 * User model hosted on the file system. This model is intended for development use only and is not
 * production-ready. Failures may result in model corruption.
 */
class FSUserSet(val store: FileSystemStore) extends UserSet with FSUserPaths {
  /**
   * Persists the changes to the model's denormalized structure in the associated store.
   * @param changes Event sequence to apply.
   */
  def apply(changes: List[UserEvent]) {
    for (change <- changes) {
      val changeType = change.getClass

      if (changeType == classOf[UserRegistered]) {
        val email = change.asInstanceOf[UserRegistered].email
        val encodedEmail = Base64.encodeBase64(email.value.getBytes).mkString
        if (!new File(emailsPath + encodedEmail).mkdir) {
          new File(duplicateEmailConflictsPath + change.userId.value).mkdir()
        }
      }
    }
  }

  /**
   * Identifies any conflicts resulting from previous attempts at denormalizing events from given sequences.
   * @return All unresolved conflicts for this model up-to the last denormalization for the associate store.
   */
  def conflicts: List[UserConflict] =
    new File(duplicateEmailConflictsPath).list.map(f => DuplicateEmail(UserId(UUID.fromString(f)))).toList

  /**
   * Removes the model from the associated store.
   */
  def destroy() {
    new File(emailsPath).mkdir()
    new File(duplicateEmailConflictsPath).mkdir()
  }
}

/**
 * User model factory for the file system.
 */
object FSUserSet {
  /**
   * Creates a new user model in the provided store. Method has no effect if the model already exists.
   * @param store Persistence store.
   * @return Model for the given store.
   */
  def createIn(store: FileSystemStore): FSUserSet = {
    // Build and then return model.
    val model = new FSUserSet(store)
    new File(model.emailsPath).mkdir()
    new File(model.duplicateEmailConflictsPath).mkdir()
    model
  }
}
