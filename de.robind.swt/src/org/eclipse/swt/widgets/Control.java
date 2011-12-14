package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.layout.LayoutData;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;

/**
 * TODO Needs to be implemented!!
 */
public abstract class Control extends Widget implements Drawable {
  Object layoutData = null;

  /**
   * TODO Needs to be implemented!!
   */
  public Control(Composite parent, int style) {
    super(parent, style);
  }

  /**
   * Returns layout data which is associated with the receiver.
   *
   * @return the receiver's layout data
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
  public Object getLayoutData() throws SWTException {
    checkWidget();
    return (this.layoutData);
  }

  /**
   * Sets the layout data associated with the receiver to the argument.
   *
   * @param layoutData the new layout data for the receiver.
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *      if <code>layoutData</code> is not derivated from {@link LayoutData}
   *    </li>
   *  </ul>
   */
  public void setLayoutData(Object layoutData) throws SWTException {
    checkWidget();

    if (!(layoutData instanceof LayoutData)) {
      throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
    }

    LayoutData data = (LayoutData)layoutData;

    try {
      // Create the layout-data
      data.createLayoutData(getDisplay().getKey());

      // Assign the layout-data to this object
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.callMethod(getDisplay().getKey(), getId(), "setLayoutData", data);
    } catch (Throwable t) {
      // TODO Need a special code?
      SWTException e = new SWTException();
      e.throwable = t;
      throw e;
    }

    this.layoutData = layoutData;
  }

  /**
   * If the argument is <code>false</code>, causes subsequent drawing
   * operations in the receiver to be ignored. No drawing of any kind can occu
   * in the receiver until the flag is set to <code>true</code>. Graphics
   * operations that occurred while the flag was <code>false</code> are lost.
   * When the flag is set to <code>true</code>, the entire widget is marked
   * as needing to be redrawn. Nested calls to this method are stacked.
   *
   * @param redraw the new redraw state
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
  public void setRedraw(boolean redraw) throws SWTException {
    checkWidget();
    getDisplay().callMethod(getId(), "setRedraw", redraw);
  }
}
