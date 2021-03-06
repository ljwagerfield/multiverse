package io.multiverse.application

import io.multiverse.application.Loan._
import scala.language.implicitConversions
import java.io.{File, OutputStreamWriter, FileOutputStream}

object SimpleIO {

  /**
   * Converts the given string into content.
   * @param content String to convert into content.
   * @return String content.
   */
  implicit def stringContent(content: String) = new StringContent(content)

  /**
   * String representing short content intended to be written to an output.
   * @param content String content.
   */
  class StringContent(content: String) {

    /**
     * Atomically writes the given content to the specified file. Any existing content is cleared before the write.
     * @param filePath File to write to.
     */
    def saveTo(filePath: String) {
      val stream = new FileOutputStream(filePath)
      loan (new OutputStreamWriter(stream)) to (writer => {
        loan(stream.getChannel.lock()) to(lock => {
          stream.getChannel.truncate(0) // Ensure contents is wiped as part of the file transaction.
          writer.write(content)
        })
      })
    }

    /**
     * Atomically writes the given content to the specified file only if it does not already exist.
     * @param filePath File to write to.
     * @return True if file was written, otherwise false if already exists
     */
    def saveToNew(filePath: String): Boolean = {
      if (new File(filePath).createNewFile()) {
        saveTo(filePath)
        true
      }
      else {
        false
      }
    }
  }
}
