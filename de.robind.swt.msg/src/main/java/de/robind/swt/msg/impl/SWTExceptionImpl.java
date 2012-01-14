package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTException;

/**
 * Implementation of the {@link SWTException}-interface.
 *
 * @author Robin Doer
 */
public class SWTExceptionImpl implements SWTException {
  /**
   * The cause-exception
   */
  private Throwable cause = null;

  public SWTExceptionImpl(Throwable cause) {
    this.cause = cause;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTException#getCause()
   */
  public Throwable getCause() {
    return (this.cause);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SWTExceptionImpl [cause=" + cause.getClass().getName() +
        ", msg=" + cause.getMessage() + "]";
  }
}
