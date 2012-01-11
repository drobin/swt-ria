package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * Instances of this class implement a selectable user interface object that
 * displays a list of images and strings and issues notification when selected.
 * <p>
 * The item children that may be added to instances of this class must be of
 * type {@link TableItem}.
 * <p>
 * Style {@link SWT#VIRTUAL} is used to create a {@link Table} whose
 * {@link TableItem}s are to be populated by the client on an on-demand basis
 * instead of up-front. This can provide significant performance improvements
 * for tables that are very large or for which TableItem population is
 * expensive (for example, retrieving values from an external source).
 * <p>
 * Here is an example of using a {@link Table} with style {@link SWT#VIRTUAL}:
 *
 * <pre>
 *  final Table table = new Table (parent, SWT.VIRTUAL | SWT.BORDER);
 *  table.setItemCount (1000000);
 *  table.addListener (SWT.SetData, new Listener () {
 *    public void handleEvent (Event event) {
 *      TableItem item = (TableItem) event.item;
 *      int index = table.indexOf (item);
 *      item.setText ("Item " + index);
 *      System.out.println (item.getText ());
 *    }
 *  });
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
 *    {@link SWT#FULL_SELECTION}, {@link SWT#HIDE_SELECTION},
 *    {@link SWT#VIRTUAL}, {@link SWT#NO_SCROLL}
 *  </dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>
 *    Selection, DefaultSelection, SetData, MeasureItem, EraseItem, PaintItem
 *  </dd>
 * </dl>
 *
 * Note: Only one of the styles {@link SWT#SINGLE}, and {@link SWT#MULTI} may
 * be specified.
 * <p>
 * IMPORTANT: This class is <i>not</i> intended to be subclassed.
 */
public class Table extends Composite {
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
  public Table(Composite parent, int style) throws SWTException {
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
