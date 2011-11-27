package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTNewRequest;

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
    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS
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
    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS
    // TODO Make sure that layout can be null

    // TODO Evaluate the response
    SWTNewRequest createRequest = layout.getNewRequest();
    getDisplay().sendMessage(createRequest);

    // TODO Evaluate the response
    SWTCallRequest callRequest = new SWTCallRequest(getId(), "setLayout", layout.getId());
    getDisplay().sendMessage(callRequest);

    this.layout = layout;
  }
}
