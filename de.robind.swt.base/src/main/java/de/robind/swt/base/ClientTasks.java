package de.robind.swt.base;

import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;


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
   * @throws SWTException failed to create the {@link ClientTasks}-instance
   */
  public static ClientTasks getClientTasks() throws SWTException {
    if (ClientTasks.instance == null) {
      try {
        ClientTasks.instance = createClientTasks();
      } catch (Exception cause) {
        SWTException e = new SWTException(SWT.ERROR_FAILED_EXEC);
        e.throwable = cause;
        throw e;
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
   * @throws SWTException The operation has resulted into an error.
   */
  public abstract void createObject(Key key, int id, Class<?> objClass,
      Object... args) throws SWTException;

  /**
   * Asks the connected client to invoke a method.
   *
   * @param key The key of the client-connection
   * @param id The id of the destination object
   * @param method Name of method to be invoked
   * @param args Arguments passed to the method
   * @return Result returned by the client-method
   * @throws SWTException The operation has resulted into an error.
   */
  public abstract Object callMethod(Key key, int id, String method,
      Object... args) throws SWTException;

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
   * @throws SWTException The operation has resulted into an error.
   */
  public abstract void registerEvent(Key key, int id, int eventType,
      boolean enable) throws SWTException;

  /**
   * Asks the client to update an attribute.
   *
   * @param key The key of the client-connection
   * @param id The if of the destination object
   * @param attrName The name of the attribute to update
   * @param attrValue The new value of the attribute
   * @throws SWTException The operation has resulted into an error.
   */
  public abstract void updateAttribute(Key key, int id, String attrName,
      Object attrValue) throws SWTException;

  /**
   * Receives an event from the client.
   * <p>
   * The method blocks the calling thread until an event arrives.
   *
   * @return The properties of the event received from the client
   * @throws InterruptedException if the current thread was interrupted
   * @throws SWTException The operation has resulted into an error.
   */
  public abstract Properties waitForEvent(Key key) throws SWTException;

  /**
   * Creates a new instance of the {@link ClientTasks}-class.
   * @return
   * @throws Exception
   */
  private static ClientTasks createClientTasks()
      throws ClassNotFoundException, IllegalAccessException,
             InstantiationException {

    String className = System.getProperty("de.robind.swt.clienttasks",
        "de.robind.swt.server.ClientTasksImpl");
    Class<? extends ClientTasks> c = Class.forName(className)
        .asSubclass(ClientTasks.class);
    return (c.newInstance());
  }
}
