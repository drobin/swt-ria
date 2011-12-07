package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTEvent;

/**
 * Implementation of {@link SWTEvent}.
 *
 * @author Robin Doer
 */
public class SWTEventImpl implements SWTEvent {
  private int objId;

  public SWTEventImpl(int objId) {
    this.objId = objId;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTEvent#getObjId()
   */
  public int getObjId() {
    return (this.objId);
  }
}
