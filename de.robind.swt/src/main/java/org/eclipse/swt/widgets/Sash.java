package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionListener;

/**
 * Instances of the receiver represent a selectable user interface object that
 * allows the user to drag a rubber banded outline of the sash within the
 * parent control.
 *
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>HORIZONTAL, VERTICAL, SMOOTH</dd>
 * <dt><b>Events:</b></dt>
 * <dd>Selection</dd>
 * </dl>
 *
 * Note: Only one of the styles HORIZONTAL and VERTICAL may be specified.
 * <p>
 * IMPORTANT: This class is intended to be subclassed only within the SWT
 * implementation.
 */
public class Sash extends Control {
  /**
   * Constructs a new instance of this class given its parent and a style value
   * describing its behavior and appearance.
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
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public Sash(Composite parent, int style) throws SWTException {
    super(parent, style);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the control is selected by the user, by sending it one of the messages
   * defined in the {@link SelectionListener} interface.
   * <p>
   * When
   * {@link SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)}
   * is called, the x, y, width, and height fields of the event object are
   * valid. If the receiver is being dragged, the event object detail field
   * contains the value {@link SWT#DRAG},
   * {@link SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)}
   * is not called.
   *
   * @param listener the listener which should be notified when the control is
   *                 selected by the user
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void addSelectionListener(SelectionListener listener)
      throws SWTException {

    addTypedListener(listener, SWT.Selection, SWT.DefaultSelection);
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when the control is selected by the user.
   *
   * @param listener the listener which should no longer be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void removeSelectionListener(SelectionListener listener)
      throws SWTException {

    removeTypedListener(listener, SWT.Selection, SWT.DefaultSelection);
  }
}
