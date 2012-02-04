package de.robind.swt.base;

import java.util.LinkedList;
import java.util.Queue;

import de.robind.swt.base.SWTBaseException.Reason;




/**
 * The base-class for all SWT-objects.
 * <p>
 * Each object is identified by an {@link #getId() id}. The identifier is
 * unique over all applications.
 *
 * @author Robin Doer
 */
public class SWTObject {
  /**
   * Next free identifier used by {@link #generateId()}.
   */
  private static int nextId = 0;

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
    SWTObjectPool.getInstance().registerObject(this);
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
   * Returns the key, which is assigned (if any) to the instance.
   *
   * @return The assigned key
   */
  public Key getKey() {
    return (this.key);
  }

  /**
   * Assigns a key to the object.
   * <p>
   * If <code>null</code> is passed to the method, the current assignment is
   * removed.
   * <p>
   * If you assign a key to the object and the changelog contains at least one
   * entry, then the changelog is emptied (executed) after the key is assigned.
   *
   * @param key The key to assign the to object
   * @throws SWTBaseException failed to execute entries from the changelog
   */
  void setKey(Key key) throws SWTBaseException {
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
   * @throws SWTBaseException failed to send or schedule creation-request
   */
  public void createObject(Object... args) throws SWTBaseException {
    createObjectAs(getClass(), args);
  }

  /**
   * Sends an object-creation-request of an object of class <code>as</code>
   * to the client.
   * <p>
   * If a {@link #getKey() key} is assigned to the object, then the request
   * is send immediately. Otherwise the request is scheduled until a
   * {@link #setKey(Key) key is available}.
   *
   * @param as Class to be created
   * @param args Arguments passed to the constructor of the class
   * @throws SWTBaseException failed to send or schedule creation-request
   */
  public void createObjectAs(final Class<?> as, final Object... args)
      throws SWTBaseException {

    ChangeLogEntry entry = new ChangeLogEntry() {
      public void run(Key key) throws SWTBaseException {
        ClientTasks tasks = ClientTasks.getClientTasks();
        tasks.createObject(key, getId(), as, args);
      }
    };

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
   * assigned, a {@link SWTBaseException} with reason {@link Reason#FailedExec}
   * is thrown.
   *
   * @param method Name of method to invoke
   * @param args Arguments which should be passed to the method
   * @return Result returned by the method-invocation
   * @throws SWTBaseException failed to send the call-request
   */
  public Object callMethod(String method, Object... args)
      throws SWTBaseException {

    if (getKey() == null) {
      throw new SWTBaseException(Reason.FailedExec);
    }

    ClientTasks clientTasks = ClientTasks.getClientTasks();
    return (clientTasks.callMethod(key, getId(), method, args));
  }

  /**
   * Sends an event-registration-message to the client.
   * <p>
   * If a {@link #getKey() key} is assigned to the object, then the request
   * is send immediately. Otherwise the request is scheduled until a
   * {@link #setKey(Key) key is available}.
   *
   * @param eventType The event-type to (un-)register
   * @param enable If set to <code>true</code>, the event-handling is enabled
   *               for the requested type, otherwise it is disabled
   * @throws SWTBaseException failed to send or schedule the request
   */
  public void registerEvent(final int eventType, final boolean enable)
      throws SWTBaseException {

    ChangeLogEntry entry = new ChangeLogEntry() {
      public void run(de.robind.swt.base.Key key) throws SWTBaseException {
        ClientTasks clientTasks = ClientTasks.getClientTasks();
        clientTasks.registerEvent(key, getId(), eventType, enable);
      }
    };

    if (getKey() != null) {
      entry.run(getKey());
    } else {
      this.changeLog.offer(entry);
    }
  }

  /**
   * Updates the value of the given attribute.
   * <p>
   * If a {@link #getKey() key} is assigned to the object, then the request
   * is send immediately. Otherwise the request is scheduled until a
   * {@link #setKey(Key) key is available}.
   *
   * @param fieldName The field-name of the attribute to update
   * @param value The value to be send to the client
   * @throws SWTBaseException failed to send or schedule the request
   */
  public void updateAttribute(final String fieldName, final Object value)
      throws SWTBaseException {

    ChangeLogEntry entry = new ChangeLogEntry() {
      public void run(Key key) throws SWTBaseException {
        ClientTasks clientTasks = ClientTasks.getClientTasks();
        clientTasks.updateAttribute(key, getId(), fieldName, value);
      }
    };

    if (getKey() != null) {
      entry.run(getKey());
    } else {
      this.changeLog.offer(entry);
    }
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
