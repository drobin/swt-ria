package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;

/**
 * TODO Needs to be implemented!!
 */
public class Composite extends Scrollable {
  private Layout layout = null;

  /**
   * TODO Needs to be implemented!!
   */
  public Composite(Composite parent, int style) {
    super(parent, style);
  }

  /**
   * Returns layout which is associated with the receiver, or <code>null</code>
   * if one has not been set.
   *
   * @return the receiver's layout or <code>null</code>
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public Layout getLayout() throws SWTException {
    checkWidget();
    return (this.layout);
  }

  /**
   * Sets the layout which is associated with the receiver to be the argument
   * which may be <code>null</code>.
   *
   * @param layout the receiver's new layout or <code>null</code>
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void setLayout(Layout layout) throws SWTException {
    checkWidget();
    // TODO Make sure that layout can be null

    try {
      // Create the layout
      layout.createLayout(getDisplay().getKey());

      // Assign the layout to this object
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.callMethod(getDisplay().getKey(), getId(), "setLayout", layout);
    } catch (Throwable t) {
      // TODO Need a special code?
      SWTException e = new SWTException();
      e.throwable = t;
      throw e;
    }

    this.layout = layout;
  }
}
