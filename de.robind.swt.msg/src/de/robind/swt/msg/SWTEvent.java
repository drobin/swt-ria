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
   * Returns the id of the object, where the event has occured.
   *
   * @return Object-id of source object
   */
  int getObjId();
}
