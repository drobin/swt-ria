package org.eclipse.swt.widgets;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Rectangle;

/**
 * This class is the abstract superclass of all classes which represent
 * controls that have standard scroll bars.
 *
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>H_SCROLL, V_SCROLL</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 *
 * IMPORTANT: This class is intended to be subclassed only within the SWT
 * implementation.
 */
public abstract class Scrollable extends Control {
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
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Scrollable(Composite parent, int style) throws SWTException {
    super(parent, style);
  }

  /**
   * Returns a rectangle which describes the area of the receiver which is
   * capable of displaying data (that is, not covered by the "trimmings").
   *
   * @return the client area
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
  public Rectangle getClientArea() throws SWTException {
    checkWidget();

    Object result = getDisplay().callMethod(getId(), "getClientArea");
    @SuppressWarnings("unchecked")
    Map<String, Object> clientAreaMap = (Map<String, Object>)result;
    int x = (Integer)clientAreaMap.get("x");
    int y = (Integer)clientAreaMap.get("y");
    int width = (Integer)clientAreaMap.get("width");
    int height = (Integer)clientAreaMap.get("height");

    return (new Rectangle(x, y, width, height));
  }
}
