package org.eclipse.swt;

import java.util.HashMap;
import java.util.Map;



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

  /**
   * Map collects all objects with their ids.
   */
  private static Map<Integer, SWTObject> objMap =
      new HashMap<Integer, SWTObject>();

  private int id;

  protected SWTObject() {
    this.id = generateId();
    SWTObject.objMap.put(this.id, this);
  }

  /**
   * Returns the (unique) id of the object.
   *
   * @return The id of the object
   */
  public int getId() {
    return (this.id);
  }

  /**
   * Searches for the object with the given id.
   *
   * @param id The id you are looking for
   * @return The object with the given id. If no such object exists,
   *         <code>null</code> is returned.
   */
  public static SWTObject findObjectById(int id) {
    return (SWTObject.objMap.get(id));
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
