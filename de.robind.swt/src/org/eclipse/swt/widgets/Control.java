package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.layout.LayoutData;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;

/**
 * Control is the abstract superclass of all windowed user interface classes.
 *
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>BORDER</dd>
 *  <dd>LEFT_TO_RIGHT, RIGHT_TO_LEFT</dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>
 *    DragDetect, FocusIn, FocusOut, Help, KeyDown, KeyUp, MenuDetect,
 *    MouseDoubleClick, MouseDown, MouseEnter, MouseExit, MouseHover, MouseUp,
 *    MouseMove, MouseWheel, MouseHorizontalWheel, MouseVerticalWheel, Move,
 *    Paint, Resize, Traverse
 *  </dd>
 * </dl>
 *
 * Only one of LEFT_TO_RIGHT or RIGHT_TO_LEFT may be specified.
 * <p>
 * IMPORTANT: This class is intended to be subclassed <i>only</i> within the
 * SWT implementation.
 */
public abstract class Control extends Widget implements Drawable {
  /**
   * Layout-data assigned to the control
   */
  Object layoutData = null;

  /**
   * Constructs a new instance of this class given its parent and a style
   * value describing its behavior and appearance.
   * <p>
   * The style value is either one of the style constants defined in class SWT
   * which is applicable to instances of this class, or must be built by
   * bitwise OR'ing together (that is, using the int "|" operator) two or more
   * of those SWT style constants. The class description lists the style
   * constants that are applicable to the class. Style bits are also inherited
   * from superclasses.
   *
   * @param parent a composite control which will be the parent of the new
   *               instance (cannot be null)
   * @param style the style of control to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the parent is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Control(Composite parent, int style) throws SWTException {
    super(parent, style);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the control is moved or resized, by sending it one of the messages
   * defined in the {@link ControlListener} interface.
   *
   * @param listener the listener which should be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void addControlListener(ControlListener listener)
      throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    TypedListenerProxy listenerProxy = new TypedListenerProxy(listener);
    addListener(SWT.Move, listenerProxy);
    addListener(SWT.Resize, listenerProxy);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * a drag gesture occurs, by sending it one of the messages defined in the
   * {@link DragDetectListener} interface.
   *
   * @param listener the listener which should be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void addDragDetectListener(DragDetectListener listener)
      throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    TypedListenerProxy listenerProxy = new TypedListenerProxy(listener);
    addListener(SWT.DragDetect, listenerProxy);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * mouse buttons are pressed and released, by sending it one of the messages
   * defined in the {@link MouseListener} interface.
   *
   * @param listener the listener which should be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void addMouseListener(MouseListener listener) throws SWTException {
    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    TypedListenerProxy listenerProxy = new TypedListenerProxy(listener);
    addListener(SWT.MouseUp, listenerProxy);
    addListener(SWT.MouseDown, listenerProxy);
    addListener(SWT.MouseDoubleClick, listenerProxy);
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
   * Removes the listener from the collection of listeners who will be notified
   * when the control is moved or resized.
   *
   * @param listener the listener which should no longer be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void removeControlListener(ControlListener listener)
      throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    removeTypedListener(SWT.Move, listener);
    removeTypedListener(SWT.Resize, listener);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when a drag gesture occurs.
   *
   * @param listener the listener which should no longer be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void removeDragDetectListener(DragDetectListener listener)
      throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    removeTypedListener(SWT.DragDetect, listener);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when mouse buttons are pressed and released.
   *
   * @param listener the listener which should no longer be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void removeMouseListener(MouseListener listener) throws SWTException {
    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    removeTypedListener(SWT.MouseUp, listener);
    removeTypedListener(SWT.MouseDown, listener);
    removeTypedListener(SWT.MouseDoubleClick, listener);
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
