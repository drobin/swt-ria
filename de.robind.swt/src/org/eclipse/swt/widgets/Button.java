package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionListener;

/**
 * Instances of this class represent a selectable user interface object that
 * issues notification when pressed and released.
 *
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>ARROW, CHECK, PUSH, RADIO, TOGGLE, FLAT</dd>
 * <dd>UP, DOWN, LEFT, RIGHT, CENTER</dd>
 * <dt><b>Events:</b></dt>
 * <dd>Selection</dd>
 * </dl>
 * <p>
 * Note: Only one of the styles {@link SWT#ARROW}, {@link SWT#CHECK},
 * {@link SWT#PUSH}, {@link SWT#RADIO}, and {@link SWT#TOGGLE} may be
 * specified.
 * <p>
 * Note: Only one of the styles {@link SWT#LEFT}, {@link SWT#RIGHT}, and
 * {@link SWT#CENTER} may be specified.
 * <p>
 * Note: Only one of the styles {@link SWT#UP}, {@link SWT#DOWN},
 * {@link SWT#LEFT}, and {@link SWT#RIGHT} may be specified when the
 * {@link SWT#ARROW} style is specified.
 * <p>
 * IMPORTANT: This class is intended to be subclassed <i>only</i> within the
 * SWT implementation.
 */
public class Button extends Control {
  /**
   * The text of the button
   */
  private String text = "";

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
  public Button(Composite parent, int style) throws SWTException {
    super(parent, style);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the control is selected by the user, by sending it one of the messages
   * defined in the {@link SelectionListener} interface.
   * <p>
   * {@link SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)}
   * is called when the control is selected by the user.
   * {@link SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)}
   * is not called.
   * <p>
   * When the SWT.RADIO style bit is set, the widgetSelected method is also
   * called when the receiver loses selection because another item in the same
   * radio group was selected by the user. During widgetSelected the
   * application can use getSelection() to determine the current selected state
   * of the receiver.
   *
   * @param listener the listener which should be notified
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

  /**
   * Returns the receiver's text, which will be an empty string if it has never
   * been set or if the receiver is an ARROW button.
   *
   * @return the receiver's text
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
  public String getText() throws SWTException {
    // TODO Test for ARROW
    checkWidget();
    return (this.text);
  }

  /**
   * Sets the receiver's text.
   * <p>
   * This method sets the button label. The label may include the mnemonic
   * character but must not contain line delimiters.
   * <p>
   * Mnemonics are indicated by an '&' that causes the next character to be the
   * mnemonic. When the user presses a key sequence that matches the mnemonic,
   * a selection event occurs. On most platforms, the mnemonic appears
   * underlined but may be emphasized in a platform specific manner. The
   * mnemonic indicator character '&' can be escaped by doubling it in the
   * string, causing a single '&' to be displayed.
   * <p>
   * Note that a Button can display an image and text simultaneously on
   * Windows (starting with XP), GTK+ and OSX. On other platforms, a Button
   * that has an image and text set into it will display the image or text that
   * was set most recently.
   *
   * @param string the new text
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the text is <code>null</code>
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void setText(String string) throws SWTException {
    if (string == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    // TODO Test for ARROW
    checkWidget();
    callMethod("setText", string);
    this.text = string;
  }
}
