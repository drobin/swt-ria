package de.robind.swt.msg.impl;

import java.util.Arrays;

import de.robind.swt.msg.SWTCallRequest;

/**
 * A message for the {@link SWTProtocol#OP_CALL}-operation of type
 * {@link SWTProtocol#TYPE_REQ}.
 *
 * @author Robin Doer
 */
public class SWTCallRequestImpl implements SWTCallRequest {
  private int objId;
  private String method;
  private Object arguments[];

  public SWTCallRequestImpl(int objId, String method,
      Object... arguments) {

    this.objId = objId;
    this.method = method;
    this.arguments = arguments;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTCallRequest#getObjId()
   */
  public int getObjId() {
    return (this.objId);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTCallRequest#getMethod()
   */
  public String getMethod() {
    return (this.method);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTCallRequest#getArguments()
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
        getMethod() + "," + Arrays.toString(getArguments()) + ")");
  }
}
