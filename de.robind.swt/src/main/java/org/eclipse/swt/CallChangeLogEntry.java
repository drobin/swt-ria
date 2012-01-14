package org.eclipse.swt;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;

/**
 * A {@link ChangeLogEntry} to call a method.
 *
 * @author Robin Doer
 */
class CallChangeLogEntry implements ChangeLogEntry {
  /**
   * The id of the object to invoke
   */
  private int id;

  /**
   * The name of the method to invoke
   */
  private String method;

  /**
   * Arguments passed to the method
   */
  private Object args[];

  /**
   * Result returned by the method-invocation.
   */
  private Object result;

  /**
   * Creates a new {@link CallChangeLogEntry}.
   *
   * @param id The id of the object to invoke
   * @param method Name of method to invoke
   * @param args Arguments passed to the method
   */
  CallChangeLogEntry(int id, String method, Object... args) {
    this.id = id;
    this.method = method;
    this.args = args;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.ChangeLogEntry#run(org.eclipse.swt.server.Key)
   */
  public void run(Key key) throws SWTException {
    try {
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      this.result =
          clientTasks.callMethod(key, this.id, this.method, this.args);
    } catch (Throwable t) {
      Throwable cause =
          (t instanceof InvocationTargetException) ? t.getCause() : t;
      // TODO Do you need a special code for the exception?
      SWTException e = new SWTException(SWT.ERROR_UNSPECIFIED);
      e.throwable = cause;
      throw e;
    }
  }

  /**
   * Returns the result returned by th method-invocation.
   *
   * @return Result of method-invocation
   */
  Object getResult() {
    return (this.result);
  }
}
