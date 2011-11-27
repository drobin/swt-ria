package org.eclipse.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Layout;

import de.robind.swt.msg.SWTNewRequest;

/**
 * TODO Needs to be implemented!!
 */
public class FillLayout extends Layout {
  /**
   * type specifies how controls will be positioned within the layout. The
   * default value is {@link SWT#HORIZONTAL}. Possible values are:
   *
   *  <ul>
   *    <li>{@link SWT#HORIZONTAL}:
   *      Position the controls horizontally from left to right
   *    </li>
   *    <li>{@link SWT#VERTICAL}:
   *      Position the controls vertically from top to bottom
   *    </li>
   *  </ul>
   *
   *  // TODO Problem: Type is public. So the application can update the type
   *  // TODO          and the change is not forwarded to the client.
   */
  public int type;

  /**
   * Constructs a new instance of this class.
   */
  public FillLayout() {
    this(SWT.HORIZONTAL);
  }

  /**
   * Constructs a new instance of this class given the type.
   * @param type the type of fill layout
   */
  public FillLayout(int type) {
    this.type = type;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Layout#getNewRequest()
   */
  protected SWTNewRequest getNewRequest() {
    return (new SWTNewRequest(getId().getId(), getClass(), type));
  }
}
