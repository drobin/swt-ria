package de.robind.swt.base;

import java.util.HashMap;
import java.util.Map;



/**
 * The {@link SWTObjectPool} maintains instances of {@link SWTObject}.
 * <p>
 * It's designed as a singleton, use {@link #getInstance()} to receive the only
 * instance.
 *
 * @author Robin Doer
 */
public class SWTObjectPool {
  /**
   * The singleton instance of the {@link SWTObjectPool}.
   */
  private static SWTObjectPool singleton = null;

  /**
   * The map contains all objects registered with
   * {@link #registerObject(SWTObject)}.
   */
  private Map<Integer, SWTObject> objMap = new HashMap<Integer, SWTObject>();

  /**
   * Returns the singleton instance of the {@link SWTObjectPool}.
   *
   * @return The singleton instance of the {@link SWTObjectPool}.
   */
  public static SWTObjectPool getInstance() {
    if (singleton == null) {
      singleton = new SWTObjectPool();
    }

    return (singleton);
  }

  /**
   * Searches for the object with the given id.
   *
   * @param id The id you are looking for
   * @return The object with the given id. If no such object exists,
   *         <code>null</code> is returned.
   */
  public SWTObject findObjectById(int id) {
    return (this.objMap.get(id));
  }

  /**
   * Registers an {@link SWTObject} at the pool.
   * <p>
   * This method is called from a {@link SWTObject}-constructor.
   *
   * @param obj The object to be registered
   */
  void registerObject(SWTObject obj) {
    this.objMap.put(obj.getId(), obj);
  }

  private SWTObjectPool() {
  }
}
