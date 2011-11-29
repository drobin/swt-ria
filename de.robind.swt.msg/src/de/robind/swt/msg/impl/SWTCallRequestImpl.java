package de.robind.swt.msg.impl;

import java.util.Arrays;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTObjectId;

/**
 * A message for the {@link SWTProtocol#OP_CALL}-operation of type
 * {@link SWTProtocol#TYPE_REQ}.
 *
 * @author Robin Doer
 */
public class SWTCallRequestImpl implements SWTCallRequest {
  private SWTObjectId destObj;
  private String method;
  private Object arguments[];

  public SWTCallRequestImpl(SWTObjectId destObj, String method,
      Object... arguments) {

    this.destObj = destObj;
    this.method = method;
    this.arguments = arguments;
  }

  /* (non-Javadoc)
   * @see de.robind.swt.msg.SWTCallRequest#getDestinationObject()
   */
  public SWTObjectId getDestinationObject() {
    return (this.destObj);
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
    return (getClass().getSimpleName() + "(" + getDestinationObject() + ", " +
        getMethod() + "," + Arrays.toString(getArguments()) + ")");
  }
}
