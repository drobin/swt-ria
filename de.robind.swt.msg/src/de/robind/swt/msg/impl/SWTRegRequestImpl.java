package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTRegRequest;

/**
 * Implementation of the {@link SWTRegRequest}-interface.
 *
 * @author Robin Doer
 */
public class SWTRegRequestImpl implements SWTRegRequest {
  private int objId;
  private int eventType;
  private boolean enable;

  public SWTRegRequestImpl(int objId, int eventType, boolean enable) {
    this.objId = objId;
    this.eventType = eventType;
    this.enable = enable;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTRegRequest#getDestinationObject()
   */
  public int getObjId() {
    return (this.objId);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTRegRequest#getEventType()
   */
  public int getEventType() {
    return (this.eventType);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTRegRequest#enable()
   */
  public boolean enable() {
    return (this.enable);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + " [objId=" + objId +
        ", eventType=" + eventType + ", enable=" + enable + "]";
  }
}
