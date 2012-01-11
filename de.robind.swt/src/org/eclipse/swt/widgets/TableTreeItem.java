package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * A TableTreeItem is a selectable user interface object that represents an
 * item in a hierarchy of items in a {@link TableTree}.
 *
 * @deprecated As of 3.1 use Tree, TreeItem and TreeColumn
 */
public class TableTreeItem extends Item {
  /**
   * Constructs a new instance of this class given its parent (which must be a
   * {@link TableTree}) and a style value describing its behavior and
   * appearance. The item is added to the end of the items maintained by its
   * parent.
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
   *
   * @deprecated
   */
  public TableTreeItem(TableTree parent, int style) throws SWTException {
    super(parent, style);

    throw new SWTException(SWT.ERROR_NOT_IMPLEMENTED);
  }
}
