package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.layout.LayoutData;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTNewRequest;

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
    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS
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
    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS

    if (!(layoutData instanceof LayoutData)) {
      throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
    }

    LayoutData data = (LayoutData)layoutData;

    // TODO Evaluate the response
    SWTNewRequest createRequest = data.getNewRequest();
    getDisplay().sendMessage(createRequest);

    // TODO Evaluate the response
    Display display = getDisplay();
    SWTMessageFactory factory = display.getMessageFactory();
    SWTCallRequest callRequest = factory.createCallRequest(getId(), "setLayoutData", data.getId());
    display.sendMessage(callRequest);

    this.layoutData = layoutData;
  }
}
