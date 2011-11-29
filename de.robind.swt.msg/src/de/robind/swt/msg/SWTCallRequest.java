package de.robind.swt.msg;

import java.util.Arrays;

/**
 * A message for the {@link SWTProtocol#OP_CALL}-operation of type
 * {@link SWTProtocol#TYPE_REQ}.
 *
 * @author Robin Doer
 */
public class SWTCallRequest implements SWTRequest {
  private SWTObjectId destObj;
  private String method;
  private Object arguments[];

  /**
   * Creates a new
   * {@link SWTProtocol#OP_CALL}-{@link SWTProtocol#TYPE_REQ}-message.
   *
   * @param destObj The id of the destination object
   * @param method The method to be invoked
   * @param arguments The arguments to the method
   * @throws NullPointerException if <code>destObj</code> or <code>method</code>
   *        are <code>null</code>
   * @throws IllegalArgumentException if you pass an empty method to the
   *         constructor
   */
  public SWTCallRequest(SWTObjectId destObj, String method, Object... arguments)
      throws NullPointerException, IllegalArgumentException {

    if (destObj == null) {
      throw new NullPointerException("destObj cannot be null");
    }

    if (!destObj.isValid()) {
      throw new IllegalArgumentException("destObj cannot be invalid");
    }

    if (method == null) {
      throw new NullPointerException("method cannot be null");
    }

    if (method.length() == 0) {
      throw new IllegalArgumentException("method cannot be an emtpy string");
    }

    this.destObj = destObj;
    this.method = method;
    this.arguments = arguments;
  }

  /**
   * Returns the id of the object, where the {@link #getMethod() method} should
   * be invoked.
   *
   * @return Object-id of destination object
   */
  public SWTObjectId getDestinationObject() {
    return (this.destObj);
  }

  /**
   * Returns the method-name which should be invoked on the
   * {@link #getDestinationObject() object}.
   *
   * @return The destination method-name
   */
  public String getMethod() {
    return (this.method);
  }

  /**
   * Returns the arguments which should be passed to the
   * {@link #getMethod() destination method}.
   *
   * @return Arguments for the method
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
