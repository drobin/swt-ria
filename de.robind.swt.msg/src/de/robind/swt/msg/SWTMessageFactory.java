package de.robind.swt.msg;

import de.robind.swt.msg.impl.SWTCallRequestImpl;
import de.robind.swt.msg.impl.SWTNewRequestImpl;


/**
 * Factory used to creates SWT-message-instances.
 *
 * @author Robin Doer
 */
public class SWTMessageFactory {
  /**
   * Creates a new {@link SWTCallRequest}-message.
   *
   * @param destObj The id of the destination object
   * @param method The method to be invoked
   * @param arguments The arguments to the method
   * @throws NullPointerException if <code>destObj</code> or <code>method</code>
   *         are <code>null</code>
   * @throws IllegalArgumentException if <code>method</code> is an empty string
   */
  public SWTCallRequest createCallRequest(SWTObjectId destObj, String method,
      Object... arguments) throws NullPointerException, IllegalArgumentException {

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
      throw new IllegalArgumentException("method cannot be an empty string");
    }

    return (new SWTCallRequestImpl(destObj, method, arguments));
  }

  /**
   * Creates a new {@link SWTNewRequest}-instance.
   *
   * @param objId The id of the object
   * @param objClass The class to be instanciated
   * @param arguments The arguments to the constructor
   * @throws NullPointerException if <code>objClass</code> is <code>null</code>
   */
  public SWTNewRequest createNewRequest(int objId, Class<?> objClass,
      Object... arguments) throws NullPointerException {

    if (objClass == null) {
      throw new NullPointerException("objClass cannot be null");
    }

    return (new SWTNewRequestImpl(objId, objClass, arguments));
  }
}
