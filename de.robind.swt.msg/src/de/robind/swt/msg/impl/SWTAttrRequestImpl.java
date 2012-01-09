package de.robind.swt.msg.impl;

import de.robind.swt.msg.SWTAttrRequest;

/**
 * Implementation of {@link SWTAttrRequest}.
 *
 * @author Robin Doer
 */
public class SWTAttrRequestImpl implements SWTAttrRequest {
  private int objId;
  private String attrName;
  private Object attrValue;

  public SWTAttrRequestImpl(int objId, String attrName, Object attrValue) {
    this.objId = objId;
    this.attrName = attrName;
    this.attrValue = attrValue;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTAttrRequest#getObjId()
   */
  public int getObjId() {
    return (this.objId);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTAttrRequest#getAttrName()
   */
  public String getAttrName() {
    return (this.attrName);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTAttrRequest#getAttrValue()
   */
  public Object getAttrValue() {
    return (this.attrValue);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() +
        " (objId=" + getObjId() + ", attrName=" + getAttrName() +
        ", attrValue=" + getAttrValue() + ")";
  }
}
