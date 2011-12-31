package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseListener;
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

    addTypedListener(listener, SWT.Move, SWT.Resize);
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

    addTypedListener(listener, SWT.DragDetect);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the control gains or loses focus, by sending it one of the messages
   * efined in the {@link FocusListener} interface.
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
  public void addFocusListener(FocusListener listener) throws SWTException {
    addTypedListener(listener, SWT.FocusIn, SWT.FocusOut);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * help events are generated for the control, by sending it one of the
   * messages defined in the {@link HelpListener} interface.
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
  public void addHelpListener(HelpListener listener) throws SWTException {
    addTypedListener(listener, SWT.Help);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * keys are pressed and released on the system keyboard, by sending it one of
   * the messages defined in the {@link KeyListener} interface.
   * <p>
   * When a key listener is added to a control, the control will take part in
   * widget traversal. By default, all traversal keys (such as the tab key and
   * so on) are delivered to the control. In order for a control to take part
   * in traversal, it should listen for traversal events. Otherwise, the user
   * can traverse into a control but not out. Note that native controls such
   * as table and tree implement key traversal in the operating system. It is
   * not necessary to add traversal listeners for these controls, unless you
   * want to override the default traversal.
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
  public void addKeyListener(KeyListener listener) throws SWTException {
    addTypedListener(listener, SWT.KeyUp, SWT.KeyDown);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the platform-specific context menu trigger has occurred, by sending it one
   * of the messages defined in the {@link MenuDetectListener} interface.
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
  public void addMenuDetectListener(MenuDetectListener listener)
      throws SWTException {

    addTypedListener(listener, SWT.MenuDetect);
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
    addTypedListener(listener,
        SWT.MouseUp, SWT.MouseDown, SWT.MouseDoubleClick);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified
   * when the mouse moves, by sending it one of the messages defined in the
   * {@link MouseMoveListener} interface.
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
  public void addMouseMoveListener(MouseMoveListener listener)
      throws SWTException {

    addTypedListener(listener, SWT.MouseMove);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the mouse passes or hovers over controls, by sending it one of the
   * messages defined in the {@link MouseTrackListener} interface.
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
  public void addMouseTrackListener(MouseTrackListener listener)
      throws SWTException {

    addTypedListener(listener, SWT.MouseEnter, SWT.MouseExit, SWT.MouseHover);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the mouse wheel is scrolled, by sending it one of the messages defined in
   * the MouseWheelListener interface.
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
  public void addMouseWheelListener(MouseWheelListener listener)
      throws SWTException {

    addTypedListener(listener, SWT.MouseWheel);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the receiver needs to be painted, by sending it one of the messages
   * defined in the {@link PaintListener} interface.
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
  public void addPaintListener(PaintListener listener) throws SWTException {
    addTypedListener(listener, SWT.Paint);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * traversal events occur, by sending it one of the messages defined in the
   * {@link TraverseListener} interface.
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
  public void addTraverseListener(TraverseListener listener)
      throws SWTException {

    addTypedListener(listener, SWT.Traverse);
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

    removeTypedListener(listener, SWT.Move, SWT.Resize);
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

    removeTypedListener(listener, SWT.DragDetect);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the control gains or loses focus.
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
  public void removeFocusListener(FocusListener listener) throws SWTException {
    removeTypedListener(listener, SWT.FocusIn, SWT.FocusOut);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the help events are generated for the control.
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
  public void removeHelpListener(HelpListener listener) throws SWTException {
    removeTypedListener(listener, SWT.Help);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when keys are pressed and released on the system keyboard.
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
  public void removeKeyListener(KeyListener listener) throws SWTException {
    removeTypedListener(listener, SWT.KeyUp, SWT.KeyDown);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the platform-specific context menu trigger has occurred.
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
  public void removeMenuDetectListener(MenuDetectListener listener)
      throws SWTException {

    removeTypedListener(listener, SWT.MenuDetect);
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
    removeTypedListener(listener,
        SWT.MouseUp, SWT.MouseDown, SWT.MouseDoubleClick);
  }

  /**
   * Removes the listener from the collection of listeners who will be
   * notified when the mouse moves.
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
  public void removeMouseMoveListener(MouseMoveListener listener)
      throws SWTException {

    removeTypedListener(listener, SWT.MouseMove);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the mouse passes or hovers over controls.
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
  public void removeMouseTrackListener(MouseTrackListener listener)
      throws SWTException {

    removeTypedListener(listener,
        SWT.MouseEnter, SWT.MouseExit, SWT.MouseHover);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the mouse wheel is scrolled.
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
  public void removeMouseWheelListener(MouseWheelListener listener)
      throws SWTException {

    removeTypedListener(listener, SWT.MouseWheel);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the receiver needs to be painted.
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
  public void removePaintListener(PaintListener listener) throws SWTException {
    removeTypedListener(listener, SWT.Paint);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when traversal events occur.
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
  public void removeTraverseListener(TraverseListener listener)
      throws SWTException {

    removeTypedListener(listener, SWT.Traverse);
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

    if (layoutData != null && !(layoutData instanceof LayoutData)) {
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
