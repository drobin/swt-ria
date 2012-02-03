package de.robind.swt.base;

/**
 * Exception thrown by the base-package.
 * <p>
 * The {@link Reason} describes the reason, why the exception was thrown. It
 * mus be passed to any constructor of the exception.
 *
 * @author Robin Doer
 */
public class SWTBaseException extends Exception {
  private static final long serialVersionUID = 4276517374100348185L;

  /**
   * The reason why the exception was thrown.
   */
  public enum Reason {
    /**
     * No special reason
     */
    Unknown,
  }

  /**
   * The reason why the exception was thrown.
   */
  private Reason reason;

  /**
   * Constructs a new exception with the specified detail message and
   * cause.
   * <p>
   * Note that the detail message associated with <code>cause</code> is
   * <i>not</i> automatically incorporated in this exception's detail message.
   *
   * @param reason The reason of the exception.
   * @param Message the detail message (which is saved for later retrieval
   *        by the {@link #getMessage()} method).
   * @param cause the cause (which is saved for later retrieval by the
   *        {@link #getCause()} method).  (A <tt>null</tt> value is
   *        permitted, and indicates that the cause is nonexistent or
   *        unknown.)
   */
  public SWTBaseException(Reason reason, String message, Throwable cause) {
    super(message, cause);

    this.reason = reason;
  }

  /**
   * Constructs a new exception with the specified detail message. The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param reason The reason of the exception.
   * @param message The detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public SWTBaseException(Reason reason, String message) {
    super(message);

    this.reason = reason;
  }

  /**
   * Constructs a new exception with the specified cause and a detail
   * message of <tt>(cause==null ? null : cause.toString())</tt> (which
   * typically contains the class and detail message of <tt>cause</tt>).
   * This constructor is useful for exceptions that are little more than
   * wrappers for other throwables (for example, {@link
   * java.security.PrivilegedActionException}).
   *
   * @param reason The reason of the exception.
   * @param cause The cause (which is saved for later retrieval by the
   *              {@link #getCause()} method).  (A <tt>null</tt> value is
   *              permitted, and indicates that the cause is nonexistent or
   *              unknown.)
   */
  public SWTBaseException(Reason reason, Throwable cause) {
    super(cause);

    this.reason = reason;
  }

  /**
   * Constructs a new exception with <code>null</code> as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   */
  public SWTBaseException(Reason reason) {
    super();

    this.reason = reason;
  }

  /**
   * Returns the reason, why the exception was thrown.
   *
   * @return The reason of the exception
   */
  public Reason getReason() {
    return (this.reason);
  }
}
