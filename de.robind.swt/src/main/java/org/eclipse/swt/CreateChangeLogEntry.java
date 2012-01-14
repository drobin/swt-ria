package org.eclipse.swt;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;

/**
 * A {@link ChangeLogEntry} to create the object.
 *
 * @author Robin Doer
 */
class CreateChangeLogEntry implements ChangeLogEntry {
  /**
   * The id of the object to create
   */
  private int id;

  /**
   * Class of the object to create
   */
  private Class<?> objClass;

  /**
   * Arguments passed to the constructor
   */
  private Object args[];

  /**
   * Creates a new {@link CreateChangeLogEntry}-instance
   *
   * @param id The id of the object to create
   * @param objClass The class of the object to create
   * @param args Arguments passed to the constructor
   */
  CreateChangeLogEntry(int id, Class<?> objClass, Object... args) {
    this.id = id;
    this.objClass = objClass;
    this.args = args;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.ChangeLogEntry#run(org.eclipse.swt.server.Key)
   */
  public void run(Key key) throws SWTException {
    try {
      ClientTasks tasks = DisplayPool.getInstance().getClientTasks();
      tasks.createObject(key, this.id, this.objClass, this.args);
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
