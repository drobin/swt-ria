package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * Instances of this class provide a selectable user interface object that
 * displays a hierarchy of items and issues notification when an item in the
 * hierarchy is selected.
 * <p>
 * The item children that may be added to instances of this class must be of
 * type {@link TreeItem}.
 * <p>
 * Style {@link SWT#VIRTUAL} is used to create a {@link Tree} whose
 * {@link TreeItem}s are to be populated by the client on an on-demand basis
 * instead of up-front. This can provide significant performance improvements
 * for trees that are very large or for which {@link TreeItem} population is
 * expensive (for example, retrieving values from an external source).
 * <p>
 * Here is an example of using a {@link Tree} with style {@link SWT#VIRTUAL}:
 *
 * <pre>
 * final Tree tree = new Tree(parent, SWT.VIRTUAL | SWT.BORDER);
 * tree.setItemCount(20);
 * tree.addListener(SWT.SetData, new Listener() {
 *   public void handleEvent(Event event) {
 *     TreeItem item = (TreeItem)event.item;
 *     TreeItem parentItem = item.getParentItem();
 *     String text = null;
 *     if (parentItem == null) {
 *       text = "node " + tree.indexOf(item);
 *     } else {
 *       text = parentItem.getText() + " - " + parentItem.indexOf(item);
 *     }
 *     item.setText(text);
 *     System.out.println(text);
 *     item.setItemCount(10);
 *   }
 * });
 * </pre>
 *
 * Note that although this class is a subclass of {@link Composite}, it does
 * not normally make sense to add {@link Control} children to it, or set a
 * layout on it, unless implementing something like a cell editor.
 *
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>
 *    {@link SWT#SINGLE}, {@link SWT#MULTI}, {@link SWT#CHECK},
 *    {@link SWT#FULL_SELECTION}, {@link SWT#VIRTUAL}, {@link SWT#NO_SCROLL}
 *  </dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>
 *    Selection, DefaultSelection, Collapse, Expand, SetData, MeasureItem,
 *    EraseItem, PaintItem
 *  </dd>
 * </dl>
 *
 * IMPORTANT: This class is <i>not</i> intended to be subclassed.
 */
public class Tree extends Composite {
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
   *               instance (cannot be <code>null</code>)
   * @param style the style of control to construct
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
   */
  public Tree(Composite parent, int style) throws SWTException {
    super(parent, style);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Composite#checkSubclass()
   */
  @Override
  protected void checkSubclass() throws SWTException {
    // Re-enable the check
    performCheckSubclass();
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Composite#getCreateClass()
   */
  @Override
  protected Class<? extends Widget> getCreateClass() {
    return (getClass());
  }
}
