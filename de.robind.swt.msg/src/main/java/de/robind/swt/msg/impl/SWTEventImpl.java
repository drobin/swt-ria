package de.robind.swt.msg.impl;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import de.robind.swt.msg.SWTEvent;

/**
 * Implementation of {@link SWTEvent}.
 *
 * @author Robin Doer
 */
public class SWTEventImpl implements SWTEvent {
  private int objId;
  private Map<String, Object> attributes;

  public SWTEventImpl(int objId, Map<String, Object> attributes) {
    this.objId = objId;

    if (attributes != null) {
      this.attributes = attributes;
    } else {
      this.attributes = new Hashtable<String, Object>();
    }
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTEvent#getObjId()
   */
  public int getObjId() {
    return (this.objId);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTEvent#getAttributes()
   */
  public String[] getAttributes() {
    Set<String> attrSet = this.attributes.keySet();
    String attrArray[] = new String[attrSet.size()];

    return (attrSet.toArray(attrArray));
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTEvent#getAttributeValue(java.lang.String)
   */
  public Object getAttributeValue(String attr) throws NullPointerException {
    if (attr == null) {
      throw new NullPointerException("attr cannot be null");
    }

    if (!this.attributes.containsKey(attr)) {
      throw new NullPointerException("No such attribute: " + attr);
    }

    return (this.attributes.get(attr));
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String attrStr = this.attributes.entrySet().toString();
    return "SWTEvent [objId=" + objId + ", attributes=" + attrStr + "]";
  }
}
