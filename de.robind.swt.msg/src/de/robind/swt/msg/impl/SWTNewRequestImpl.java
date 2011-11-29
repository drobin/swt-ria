package de.robind.swt.msg.impl;

import java.util.Arrays;

import de.robind.swt.msg.SWTNewRequest;

/**
 * Implementation of the {@link SWTNewRequest}-interface.
 *
 * @author Robin Doer
 */
public class SWTNewRequestImpl implements SWTNewRequest {
  private int objId;
  private Class<?> objClass = null;
  private Object arguments[] = {};

  public SWTNewRequestImpl(int objId, Class<?> objClass, Object... arguments) {
    this.objId = objId;
    this.objClass = objClass;
    this.arguments = arguments;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTNewRequest#getObjId()
   */
  public int getObjId() {
    return (this.objId);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTNewRequest#getObjClass()
   */
  public Class<?> getObjClass() {
    return (this.objClass);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTNewRequest#getArguments()
   */
  public Object[] getArguments() {
    return (this.arguments);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (getClass().getSimpleName() + "(" + getObjId() + ", " +
        getObjClass().getName() + "," + Arrays.toString(getArguments()) + ")");
  }
}
