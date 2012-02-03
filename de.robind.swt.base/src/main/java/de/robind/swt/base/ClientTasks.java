package de.robind.swt.base;

import java.util.Properties;

import de.robind.swt.base.SWTBaseException.Reason;


/**
 * The interface to the application-server.
 * <p>
 * The class defines various methods, which performs some actions on the
 * application-server. The concrete application-server provides a concreate
 * implementation of the class. The caller uses {@link #getClientTasks()} to
 * fetch the instance.
 *
 * @author Robin Doer
 */
public abstract class ClientTasks {
  /**
   * The instance of the client-tasks
   */
  private static ClientTasks instance = null;

  /**
   * Retuns an instance of the {@link ClientTasks}.
   * <p>
   * The object can be used to communicate with the connected client.
   *
   * @return The {@link ClientTasks}-instance
   */
  public static ClientTasks getClientTasks() throws SWTBaseException {
    if (ClientTasks.instance == null) {
      try {
        ClientTasks.instance = createClientTasks();
      } catch (Exception e) {
        throw new SWTBaseException(Reason.ClientTasks, e);
      }
    }

    return (ClientTasks.instance);
  }

  /**
   * Asks the connected client to create an object.
   *
   * @param key The key of the client-connection
   * @param id The id of the object to be created
   * @param objClass The class of the object to be created
   * @param args Arguments which should be passed to the constructor
   * @throws SWTBaseException Exception with reason {@link Reason#AppServer}.
   *         The operation has resulted into an error.
   */
  public abstract void createObject(Key key, int id, Class<?> objClass,
      Object... args) throws SWTBaseException;

  /**
   * Asks the connected client to invoke a method.
   *
   * @param key The key of the client-connection
   * @param id The id of the destination object
   * @param method Name of method to be invoked
   * @param args Arguments passed to the method
   * @return Result returned by the client-method
   * @throws SWTBaseException Exception with reason {@link Reason#AppServer}.
   *         The operation has resulted into an error.
   */
  public abstract Object callMethod(Key key, int id, String method,
      Object... args) throws SWTBaseException;

  /**
   * Asks the client to enable event-handling for an object.
   *
   * @param key The key of the client-connection
   * @param id The id of the destination object
   * @param eventType the event-type, where the event-handling should be
   *                  enabed/disabled.
   * @param enable if set to <code>true</code>, the event-handling should be
   *               enabled. If set to <code>false</code>, it should be
   *               disabled.
   * @throws SWTBaseException Exception with reason {@link Reason#AppServer}.
   *         The operation has resulted into an error.
   */
  public abstract void registerEvent(Key key, int id, int eventType,
      boolean enable) throws SWTBaseException;

  /**
   * Asks the client to update an attribute.
   *
   * @param key The key of the client-connection
   * @param id The if of the destination object
   * @param attrName The name of the attribute to update
   * @param attrValue The new value of the attribute
   * @throws SWTBaseException Exception with reason {@link Reason#AppServer}.
   *         The operation has resulted into an error.
   */
  public abstract void updateAttribute(Key key, int id, String attrName,
      Object attrValue) throws SWTBaseException;

  /**
   * Receives an event from the client.
   * <p>
   * The method blocks the calling thread until an event arrives.
   *
   * @return The properties of the event received from the client
   * @throws InterruptedException if the current thread was interrupted
   * @throws SWTBaseException Exception with reason {@link Reason#AppServer}.
   *         The operation has resulted into an error.
   */
  public abstract Properties waitForEvent(Key key) throws SWTBaseException;

  /**
   * Creates a new instance of the {@link ClientTasks}-class.
   * @return
   * @throws Exception
   */
  private static ClientTasks createClientTasks() throws Exception {
    String className = System.getProperty("de.robind.swt.clienttasks",
        "de.robind.swt.server.ClientTasksImpl");
    Class<? extends ClientTasks> c = Class.forName(className)
        .asSubclass(ClientTasks.class);
    return (c.newInstance());
  }
}
