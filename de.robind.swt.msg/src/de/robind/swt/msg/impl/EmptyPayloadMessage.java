package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.msg.SWTRegResponse;

/**
 * A message with an empty payload.
 *
 * @author Robin Doer
 */
public class EmptyPayloadMessage implements SWTNewResponse, SWTRegResponse {
  private String className = null;

  /**
   * Creates an new {@link EmptyPayloadMessage}-instance.
   *
   * @param className Identifier used in {@link #toString()}.
   */
  public EmptyPayloadMessage(String className) {
    this.className = className;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (this.className + "(success)");
  }
}
