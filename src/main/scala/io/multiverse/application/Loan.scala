package io.multiverse.application

/**
 * Loan for a given resource.
 * @param resource Resource being loaned.
 * @tparam A Type of resource being loaned.
 */
class Loan[A <: AutoCloseable](resource: A) {
  /**
   * Uses and then relinquishes the loaned resource.
   * @param block Loan recipient.
   * @tparam B Return value from loan recipient.
   */
  def to[B](block: A => B) {
    var t: Throwable = null
    try {
      block(resource)
    } catch {
      case x: Throwable => t = x; throw x
    } finally {
      if (resource != null) {
        if (t != null) {
          try {
            resource.close()
          } catch {
            case y: Throwable => t.addSuppressed(y)
          }
        } else {
          resource.close()
        }
      }
    }
  }
}

/**
 * Loan factory.
 */
object Loan {
  /**
   * Creates a loan. The provided resource will be closed after the receiver of the loan completes.
   * @param resource The closable resource.
   * @tparam A Resource type.
   * @return Resource loan.
   */
  def loan[A <: AutoCloseable](resource: A) = new Loan(resource)
}
