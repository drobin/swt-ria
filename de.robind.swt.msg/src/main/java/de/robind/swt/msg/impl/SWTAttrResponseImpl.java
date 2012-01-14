package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTAttrResponse;

/**
 * Implementation of {@link SWTAttrResponse}.
 *
 * @author Robin Doer
 */
public class SWTAttrResponseImpl implements SWTAttrResponse {
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (getClass().getSimpleName() + "(success)");
  }
}
