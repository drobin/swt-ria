package de.robind.swt.client;

import java.util.HashMap;
import java.util.Map;


/**
 * Mapping from an object-id to the SWT-object.
 *
 * @author Robin Doer
 */
public class SWTObjectMap {
  private Map<Integer, Object> objMap = new HashMap<Integer, Object>();

  /**
   * Returns the object behind the given id.
   *
   * @param id The requested id
   * @return The object behind the id. If no such object exists,
   *         <code>null</code> is returned.
   */
  public Object get(int id) {
    synchronized (objMap) {
     return (this.objMap.get(id));
    }
  }

  /**
   * Registers a object in the object-map
   * @param id The id of the object
   * @param obj The object to be registered
   */
  public void put(int id, Object obj) {
    synchronized (obj) {
     this.objMap.put(id, obj);
    }
  }
}
