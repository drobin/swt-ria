package de.robind.swt.msg;

/**
 * An event send from the client back to the server.
 * <p>
 * An event is not answered.
 *
 * @author Robin Doer
 */
public interface SWTEvent extends SWTMessage {
  /**
   * Returns the id of the object, where the event occured.
   *
   * @return Object-id of source object
   */
  int getObjId();

  /**
   * Returns an array with the names of all attributes inside the event.
   *
   * @return An array with all attribute-names
   */
  String[] getAttributes();

  /**
   * Returns the value of a specific attribute.
   *
   * @param attr The name of the attribute. This is one returned by
   *             {@link #getAttributes()}.
   * @return The value of the attribute
   * @throws NullPointerException if <code>attr</code> is <code>null</code> or
   *                              if <code>attr</code> does not exist
   */
  Object getAttributeValue(String attr) throws NullPointerException;
}
