package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A TableTree is a selectable user interface object that displays a hierarchy
 * of items, and issues notification when an item is selected. A TableTree may
 * be single or multi select.
 * <p>
 * The item children that may be added to instances of this class must be of
 * type {@link TableTreeItem}.
 * <p>
 * Note that although this class is a subclass of {@link Composite}, it does
 * not make sense to add {@link Control} children to it, or set a layout on it.
 *
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>
 *    {@link SWT#SINGLE}, {@link SWT#MULTI}, {@link SWT#CHECK},
 *    {@link SWT#FULL_SELECTION}
 *  </dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>Selection, DefaultSelection, Collapse, Expand</dd>
 * </dl>
 *
 * Note: Only one of the styles {@link SWT#SINGLE}, and {@link SWT#MULTI} may
 * be specified.
 *
 * @deprecated As of 3.1 use Tree, TreeItem and TreeColumn
 */
public class TableTree extends Composite {
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
   * @param parent a widget which will be the parent of the new instance
   *               (cannot be <code>null</code>)
   * @param style the style of widget to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the parent is null
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   *
   *  @deprecated
   */
  public TableTree(Composite parent, int style) throws SWTException {
    super(parent, style);

    throw new SWTException(SWT.ERROR_NOT_IMPLEMENTED);
  }
}
