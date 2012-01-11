package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * Instances of this class represent a selectable user interface object that
 * represents an item in a table.
 *
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>(none)</dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>(none)</dd>
 * </dl>
 *
 * IMPORTANT: This class is <i>not</i> intended to be subclassed.
 */
public class TableItem extends Item {
  /**
   * Constructs a new instance of this class given its parent (which must be a
   * {@link Table}) and a style value describing its behavior and appearance.
   * The item is added to the end of the items maintained by its parent.
   * <p>
   * The style value is either one of the style constants defined in class SWT
   * which is applicable to instances of this class, or must be built by
   * bitwise OR'ing together (that is, using the int "|" operator) two or more
   * of those SWT style constants. The class description lists the style
   * constants that are applicable to the class. Style bits are also inherited
   * from superclasses.
   *
   * @param parent a composite control which will be the parent of the new
   *               instance (cannot be <code>null</code>)
   * @param style the style of control to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the parent is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public TableItem(Table parent, int style) throws SWTException {
    super(parent, style);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Item#checkSubclass()
   */
  @Override
  protected void checkSubclass() throws SWTException {
    // Re-enable the check
    performCheckSubclass();
  }
}
