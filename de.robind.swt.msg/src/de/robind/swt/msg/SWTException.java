package de.robind.swt.msg;

/**
 * The operation forced by the request has resulted into an exception.
 *
 * @author Robin Doer
 */
public interface SWTException extends SWTResponse {
  /**
   * Returns the cause-exception.
   *
   * @return The cause-exception
   */
  Throwable getCause();
}
