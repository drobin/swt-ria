package de.robind.swt.msg;

import java.util.Arrays;

/**
 * A message for the {@link SWTProtocol#OP_NEW}-operation of type
 * {@link SWTProtocol#TYPE_REQ}.
 *
 * @author Robin Doer
 */
public class SWTNewRequest implements SWTOpNew, SWTRequest {
  private int objId;
  private Class<?> objClass = null;
  private Object arguments[] = {};

  /**
   * Creates a new
   * {@link SWTProtocol#OP_NEW}-{@link SWTProtocol#TYPE_REQ}-message.
   *
   * @param objId The id of the object
   * @param objClass The class to be instanciated
   * @param arguments The arguments to the constructor
   * @throws NullPointerException if <code>objClass</code> is <code>null</code>
   */
  public SWTNewRequest(int objId, Class<?> objClass, Object... arguments) {
    if (objClass == null) {
      throw new NullPointerException("objClass cannot be null");
    }

    this.objId = objId;
    this.objClass = objClass;
    this.arguments = arguments;
  }

  /**
   * Creates a new
   * {@link SWTProtocol#OP_NEW}-{@link SWTProtocol#TYPE_REQ}-message.
   *
   * @param objId The id of the object
   * @param className The class-name to be instanciated
   * @param arguments The arguments to the constructor
   * @throws NullPointerException if <code>className</code> is <code>null</code>
   * @throws ClassNotFoundException if the class <code>className</code> could
   *         not be located.
   */
  public SWTNewRequest(int objId,String className, Object... arguments)
      throws ClassNotFoundException {

    this(objId, Class.forName(className), arguments);
  }

  /**
   * Returns the id of the object.
   *
   * @return The object-id
   */
  public int getId() {
    return (this.objId);
  }

  /**
   * Returns the class to be instanciated.
   *
   * @return The class to be instanciated
   */
  public Class<?> getObjClass() {
    return (this.objClass);
  }

  /**
   * Returns the arguments which are passed to the constructor.
   *
   * @return Arguments passed to the constructor
   */
  public Object[] getArguments() {
    return (this.arguments);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (getClass().getSimpleName() + "(" + getId() + ", " +
        getObjClass().getName() + "," + Arrays.toString(getArguments()) + ")");
  }
}
