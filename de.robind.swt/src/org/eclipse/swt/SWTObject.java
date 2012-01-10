package org.eclipse.swt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
   * The changelog collects actions until a
   * {@link #setKey(Key) key is assigned}.
   */
  private Queue<ChangeLogEntry> changeLog = new LinkedList<ChangeLogEntry>();

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
  public void setKey(Key key) {
    if (key != null) {
      this.key = key;

      while (!this.changeLog.isEmpty()) {
        ChangeLogEntry entry = this.changeLog.poll();
        entry.run(key);
      }
    } else {
      this.key = null;
    }
  }

  /**
   * Sends an object-creation-request to the client.
   * <p>
   * If a {@link #getKey() key} is assigned to the object, then the request
   * is send immediately. Otherwise the request is scheduled until a
   * {@link #setKey(Key) key is available}.
   *
   * @param args Arguments passed to the constructor of the class
   * @throws SWTException failed to send or schedule creation-request
   */
  public void createObject(Object... args) throws SWTException {
    ChangeLogEntry entry =
        new CreateChangeLogEntry(getId(), SWTObject.class, args);

    if (getKey() != null) {
      entry.run(getKey());
    } else {
      this.changeLog.offer(entry);
    }
  }

  /**
   * Invokes a method.
   * <p>
   * <b>NOTE:</b> A method-invocation cannot be scheduled! So, if no key is
   * assigned, a {@link SWTException} with code {@link SWT#ERROR_FAILED_EXEC}
   * is thrown.
   *
   * @param method Name of method to invoke
   * @param args Arguments which should be passed to the method
   * @return Result returned by the method-invocation
   * @throws SWTException failed to send the call-request
   */
  public Object callMethod(String method, Object... args)
      throws SWTException {

    if (getKey() == null) {
      throw new SWTException(SWT.ERROR_FAILED_EXEC);
    }

    CallChangeLogEntry entry = new CallChangeLogEntry(getId(), method, args);
    entry.run(getKey());
    return (entry.getResult());
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
