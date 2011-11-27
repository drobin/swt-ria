package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import de.robind.swt.msg.SWTCallRequest;

/**
 * TODO Needs to be implemented!!
 */
public class Decorations extends Canvas {
  private String text = "";

  /**
   * TODO Needs to be implemented!!
   */
  public Decorations(Composite parent, int style) {
    super(parent, style);
  }

  /**
   * Returns the receiver's text, which is the string that the window manager
   * will typically display as the receiver's <i>title</i>.
   * If the text has not previously been set, returns an empty string.
   *
   * @return the text
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
    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS
    return (this.text);
  }

  /**
   * Sets the receiver's text, which is the string that the window manager will
   * typically display as the receiver's title, to the argument, which must not
   * be <code>null</code>.
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

    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS

    // TODO Evaluate the response
    SWTCallRequest request = new SWTCallRequest(getId(), "setText", string);
    getDisplay().sendMessage(request);

    this.text = string;
  }
}
