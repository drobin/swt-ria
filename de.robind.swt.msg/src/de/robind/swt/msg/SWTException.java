package de.robind.swt.msg;

/**
 * The operation forced by the request has resulted into an exception.
 *
 * @author Robin Doer
 */
public class SWTException implements SWTResponse {
  /**
   * The cause-exception
   */
  private Throwable cause = null;

  /**
   * Creates a new {@link SWTException}-message.
   *
   * @param cause The cause exception
   * @throws NullPointerException if <code>cause</code> is <code>null</code>
   */
  public SWTException(Throwable cause) throws NullPointerException {
    if (cause == null) {
      throw new NullPointerException("cause cannot be null");
    }

    this.cause = cause;
  }

  /**
   * Returns the cause-exception.
   *
   * @return The cause-exception
   */
  public Throwable getCause() {
    return (this.cause);
  }
}
