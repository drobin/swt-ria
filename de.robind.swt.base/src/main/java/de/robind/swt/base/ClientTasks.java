package de.robind.swt.base;

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
