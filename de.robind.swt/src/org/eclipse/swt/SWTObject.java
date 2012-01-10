package org.eclipse.swt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.server.Key;



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

  /**
   * The id of the object.
   */
  private int id;

  /**
   * The key assigned to the object.
   */
  private Key key = null;

  /**
   * Creates a new {@link SWTObject}-instance and assigns an unique
   * {@link #getId() id} to the instance.
   */
  protected SWTObject() {
    this.id = generateId();
    SWTObject.objMap.put(this.id, this);
  }

  /**
   * Creates a new {@link SWTObject}-instance and assigns a unique
   * {@link #getId()} and the given <code>key</code> to the instance.
   *
   * @param key The key to assign
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the key is <code>null</code>
   *    </li>
   *  </ul>
   */
  protected SWTObject(Key key) throws SWTException {
    this();

    if (key != null) {
      setKey(key);
    } else {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }
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
   * Returns the key, which is assigned (if any) to the instance.
   *
   * @return The assigned key
   */
  public Key getKey() {
    return (this.key);
  }

  /**
   * Assigns a key to the instance.
   * <p>
   * If <code>null</code> is passed to the method, the current assignment is
   * removed.
   *
   * @param key The key to assign or <code>null</code> to remove assignment
   */
  protected void setKey(Key key) {
    this.key = key;
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
