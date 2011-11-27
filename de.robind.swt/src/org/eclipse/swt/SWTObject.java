package org.eclipse.swt;

import de.robind.swt.msg.SWTObjectId;


/**
 * The base-class for all SWT-objects.
 * <p>
 * Each object is identified by an {@link #getId() id}.
 *
 * @author Robin Doer
 */
public class SWTObject {
  /**
   * Next free identifier used by {@link #generateId()}.
   */
  private static int nextId = 0;

  private SWTObjectId id;

  protected SWTObject() {
    this.id = new SWTObjectId(generateId());
  }

  /**
   * Returns the (unique) id of the object.
   *
   * @return The id of the object
   */
  public SWTObjectId getId() {
    return (this.id);
  }

  /**
   * Generates a unique identifier.
   *
   * @return A unique identifier
   */
  private int generateId() {
    return (++nextId);
  }
}
