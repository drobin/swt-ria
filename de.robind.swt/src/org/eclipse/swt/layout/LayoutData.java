package org.eclipse.swt.layout;

import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * Data which can be assiged to {@link Control#setLayoutData(Object)}.
 *
 * @author Robin Doer
 */
public abstract class LayoutData extends SWTObject {
  /**
   * Asks the layout to send a creation-request to the client.
   * <p>
   * The layout-data is not a part of the SWT-object-tree. Such an instance is
   * assigned to a {@link Control}. Thats why you don't have an association to
   * the top-level {@link Display} und you are not able to access
   * the {@link Display#getKey() key} directly.
   * <p>
   * The implementation of the method should call
   * {@link ClientTasks#createObject(Key, int, Class, Object...)} with the
   * <code>key</code> passed as an argument.
   *
   * @param key The key of the application
   * @throws Throwable failed to create the layout-data at the client
   */
  public abstract void createLayoutData(Key key) throws Throwable;
}
