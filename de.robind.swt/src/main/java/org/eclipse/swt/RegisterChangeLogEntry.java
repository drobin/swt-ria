package org.eclipse.swt;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;

/**
 * An {@link ChangeLogEntry} to register an event-type.
 *
 * @author Robin Doer
 */
class RegisterChangeLogEntry implements ChangeLogEntry {
  /**
   * The id of the destination-object
   */
  private int id;

  /**
   * The event-type to enable/disable
   */
  private int eventType;

  /**
   * Flag to enable/disable event-type
   */
  private boolean enable;

  /**
   * Creates a new {@link RegisterChangeLogEntry}-instance.
   *
   * @param id The id of the destination-object
   * @param eventType The event-type to enable/disable
   * @param enable Flag to enable/disable the event-type
   */
  RegisterChangeLogEntry(int id, int eventType, boolean enable) {
    this.id = id;
    this.eventType = eventType;
    this.enable = enable;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.ChangeLogEntry#run(org.eclipse.swt.server.Key)
   */
  public void run(Key key) throws SWTException {
    try {
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.registerEvent(key, this.id, this.eventType, this.enable);
    } catch (Throwable t) {
      Throwable cause =
          (t instanceof InvocationTargetException) ? t.getCause() : t;
      // TODO Do you need a special code for the exception?
      SWTException e = new SWTException(SWT.ERROR_UNSPECIFIED);
      e.throwable = cause;
      throw e;
    }
  }

}
