package org.eclipse.swt.widgets;

import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;

/**
 * TODO Needs to be implemented!!
 */
public abstract class Layout extends SWTObject {
  /**
   * TODO Needs to be implemented!!
   */
  public Layout() {

  }

  /**
   * Asks the layout to send a creation-request to the client.
   * <p>
   * The layout is not a part of the SWT-object-tree. A layout is assigned to
   * a {@link Composite}-instance. Thats why you don't have an association to
   * the top-level {@link Display} und you are not able to access the
   * {@link Display#getKey() key} directly.
   * <p>
   * The implementation of the method should call
   * {@link ClientTasks#createObject(Key, int, Class, Object...)} with the
   * <code>key</code> passed as an argument.
   *
   * @param key The key of the application
   * @throws Throwable failed to create the layout at the client
   */
  protected abstract void createLayout(Key key) throws Throwable;
}
