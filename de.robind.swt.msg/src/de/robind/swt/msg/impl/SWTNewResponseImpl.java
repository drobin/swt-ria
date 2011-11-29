package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTNewResponse;

/**
 * Implementation of the {@link SWTNewResponse}-interface.
 *
 * @author Robin Doer
 */
public class SWTNewResponseImpl implements SWTNewResponse {
  public SWTNewResponseImpl() {
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (getClass().getSimpleName() + "(success)");
  }
}
