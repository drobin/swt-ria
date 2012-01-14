package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTCallResponse;

/**
 * Implementation of the {@link SWTCallResponse}-interface.
 *
 * @author Robin Doer
 */
public class SWTCallResponseImpl implements SWTCallResponse {
  private Object result = null;

  public SWTCallResponseImpl(Object result) {
    this.result = result;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTCallResponse#getResult()
   */
  public Object getResult() {
    return (this.result);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (getClass().getSimpleName() + "(" + getResult() + ")");
  }
}
