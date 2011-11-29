package de.robind.swt.msg;

/**
 * The request-message for the event-registration-message.
 *
 * @author Robin Doer
 */
public interface SWTRegRequest extends SWTRequest {
  /**
   * Returns the object-id of the object, where event-handling should be
   * enabled/disabled.
   *
   * @return The destination object, where the operation should be applied.
   * TODO Maybe it is enough to specify an int here
   */
  SWTObjectId getDestinationObject();

  /**
   * Returns the event-type, where the event-handling should be
   * enabed/disabled.
   *
   * @return The requested event-type
   */
  int getEventType();

  /**
   * Tests whether event-handling should be enabled or disabled.
   *
   * @return If <code>true</code> is returned, then the event-handling should
   *         be enabled. If it is <code>false</code>, then it should be
   *         disabled.
   */
  boolean enable();
}
