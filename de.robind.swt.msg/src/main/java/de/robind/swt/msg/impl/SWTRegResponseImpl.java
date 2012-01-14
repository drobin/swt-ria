package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTRegResponse;

/**
 * Implementation of the {@link SWTRegResponse}-interface.
 *
 * @author Robin Doer
 */
public class SWTRegResponseImpl implements SWTRegResponse {
  public SWTRegResponseImpl() {
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (getClass().getSimpleName() + "(success)");
  }
}
