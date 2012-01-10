package org.eclipse.swt;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;

/**
 * A {@link ChangeLogEntry} to update an attribute.
 *
 * @author Robin Doer
 */
class AttributeChangeLogEntry implements ChangeLogEntry {
  /**
   * The id of the object to update
   */
  private int id;

  /**
   * Name of the field to update
   */
  private String fieldName;

  /**
   * The new value
   */
  private Object value;

  /**
   * Creates a new {@link AttributeChangeLogEntry}.
   *
   * @param id The id of the object to update
   * @param fieldName Name of the field to update
   * @param value The new value
   */
  AttributeChangeLogEntry(int id, String fieldName, Object value) {
    this.id = id;
    this.fieldName = fieldName;
    this.value = value;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.ChangeLogEntry#run(org.eclipse.swt.server.Key)
   */
  public void run(Key key) throws SWTException {
    try {
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.updateAttribute(key, this.id, this.fieldName, this.value);
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
