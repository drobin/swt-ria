package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import de.robind.swt.msg.SWTCallRequest;

/**
 * TODO Needs to be implemented!!
 */
public class Text extends Scrollable {
  private String text = "";

  /**
   * TODO Needs to be implemented!!
   */
  public Text(Composite parent, int style) {
    super(parent, style);
  }

  /**
   * Returns the widget text.
   * <p>
   * The text for a text widget is the characters in the widget, or an empty
   * string if this has never been set.
   *
   * @return the widget text
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public String getText() throws SWTException {
    // TODO Check for ERROR_THREAD_INVALID_ACCESS, ERROR_DEVICE_DISPOSED
    return (this.text);
  }

  /**
   * Sets the contents of the receiver to the given string. If the receiver
   * has style SINGLE and the argument contains multiple lines of text, the
   * result of this operation is undefined and may vary from platform to
   * platform.
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

    // TODO Check for ERROR_THREAD_INVALID_ACCESS, ERROR_DEVICE_DISPOSED

    // TODO Evaluate answer
    SWTCallRequest request = new SWTCallRequest(getId(), "setText", string);
    getDisplay().sendMessage(request);

    this.text = string;
  }
}
