package de.robind.swt.msg;

/**
 * The request-message for the event-registration-message.
 *
 * @author Robin Doer
 */
public class SWTRegRequest implements SWTRequest {
  private SWTObjectId objId = null;
  private int eventType;
  private boolean enable;

  /**
   * Creates a new {@link SWTRegRequest}-instance.
   *
   * @param objId The id of the destination object
   * @param eventType Th event-type to en-/disable
   * @param enable if set to <code>true</code>, the event-handling is
   *               enabled, otherwise it is disabled.
   *
   * @throws NullPointerException if <code>objId</code> is <code>null</code>
   */
  public SWTRegRequest(SWTObjectId objId, int eventType, boolean enable)
      throws NullPointerException {

    if (objId == null) {
      throw new NullPointerException("objId cannot be null");
    }

    this.objId = objId;
    this.eventType = eventType;
    this.enable = enable;
  }

  /**
   * Returns the object-id of the object, where event-handling should be
   * enabled/disabled.
   *
   * @return The destination object, where the operation should be applied.
   */
  public SWTObjectId getDestinationObject() {
    return (this.objId);
  }

  /**
   * Returns the event-type, where the event-handling should be
   * enabed/disabled.
   *
   * @return The requested event-type
   */
  public int getEventType() {
    return (this.eventType);
  }

  /**
   * Tests whether event-handling should be enabled or disabled.
   *
   * @return If <code>true</code> is returned, then the event-handling should
   *         be enabled. If it is <code>false</code>, then it should be
   *         disabled.
   */
  public boolean enable() {
    return (this.enable);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + " [objId=" + objId +
        ", eventType=" + eventType + ", enable=" + enable + "]";
  }
}
