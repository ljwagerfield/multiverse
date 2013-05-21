package io.multiverse.application.persistence

import java.nio.file.Files

/**
 * Reference to a persistence store hosted using the file system.
 */
case class FileSystemStore(path:String) extends PersistenceStore

/**
 * Creates random file system stores.
 */
object FileSystemStore {
  /**
   * Creates a random store in a temporary location on the file system.
   * @return New store.
   */
  def randomStore: FileSystemStore = new FileSystemStore(Files.createTempDirectory(null).toString)
}
