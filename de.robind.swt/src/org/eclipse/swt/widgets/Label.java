package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import de.robind.swt.msg.SWTCallRequest;

/**
 * Instances of this class represent a non-selectable user interface objec
 * that displays a string or image. When SEPARATOR is specified, displays a
 * single vertical or horizontal line.
 * <p>
 * Shadow styles are hints and may not be honored by the platform. To create a
 * separator label with the default shadow style for the platform, do not
 * specify a shadow style.
 * <p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>SEPARATOR, HORIZONTAL, VERTICAL</dd>
 * <dd>SHADOW_IN, SHADOW_OUT, SHADOW_NONE</dd>
 * <dd>CENTER, LEFT, RIGHT, WRAP</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 * <p>
 * Note: Only one of SHADOW_IN, SHADOW_OUT and SHADOW_NONE may be specified.
 * SHADOW_NONE is a HINT. Only one of HORIZONTAL and VERTICAL may be specified.
 * Only one of CENTER, LEFT and RIGHT may be specified.
 * <p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 */
public class Label extends Control {
  private String text = "";

  /**
   * TODO Needs to be implemented!!
   */
  public Label(Composite parent, int style) {
    super(parent, style);
  }

  /**
   * Returns the receiver's text, which will be an empty string if it has
   * never been set or if the receiver is a SEPARATOR label.
   *
   * @return the receiver's text
   * @throws SWTException
   * <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public String getText() throws SWTException {
    if ((getStyle() & SWT.SEPARATOR) == 0) {
      return (this.text);
    } else {
      return ("");
    }
  }

  /**
   * Sets the receiver's text.
   * <p>
   * This method sets the widget label. The label may include the mnemonic
   * character and line delimiters.
   * <p>
   * Mnemonics are indicated by an '&' that causes the next character to be the
   * mnemonic. When the user presses a key sequence that matches the mnemonic,
   * focus is assigned to the control that follows the label. On most platforms,
   * the mnemonic appears underlined but may be emphasised in a platform
   * specific manner. The mnemonic indicator character '&' can be escaped by
   * doubling it in the string, causing a single '&' to be displayed.
   *
   * @param string the new text
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the text is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void setText(String string) throws SWTException {
    if (string == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS

    // TODO Evaluate answer
    SWTCallRequest request = new SWTCallRequest(getId(), "setText", string);
    getDisplay().sendMessage(request);

    this.text = string;
  }
}
