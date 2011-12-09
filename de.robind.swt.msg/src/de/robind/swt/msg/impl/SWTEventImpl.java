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
  private Map<String, Object> attributes;

  public SWTEventImpl(Map<String, Object> attributes) {
    if (attributes != null) {
      this.attributes = attributes;
    } else {
      this.attributes = new Hashtable<String, Object>();
    }
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
}
