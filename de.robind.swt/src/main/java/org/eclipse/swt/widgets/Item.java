package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * This class is the abstract superclass of all non-windowed user interface
 * objects that occur within specific controls. For example, a tree will
 * contain tree items.
 *
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>(none)</dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>(none)</dd>
 * </dl>
 */
public abstract class Item extends Widget {
  /**
   * Constructs a new instance of this class given its parent and a style value
   * describing its behavior and appearance. The item is added to the end of
   * the items maintained by its parent.
   * <p>
   * The style value is either one of the style constants defined in class SWT
   * which is applicable to instances of this class, or must be built by
   * bitwise OR'ing together (that is, using the int "|" operator) two or more
   * of those SWT style constants. The class description lists the style
   * constants that are applicable to the class. Style bits are also inherited
   * from superclasses.
   *
   * @param parent a widget which will be the parent of the new instance
   *               (cannot be <code>null</code>)
   * @param style the style of item to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the parent is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *  </ul>
   */
  public Item(Widget parent, int style) throws SWTException {
    super(parent, style);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Widget#checkSubclass()
   */
  @Override
  protected void checkSubclass() throws SWTException {
    // remove check, this class can be subclassed
  }
}
