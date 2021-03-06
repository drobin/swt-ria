package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * Instances of this class represent a selectable user interface object that
 * displays a list of strings and issues notification when a string is
 * selected. A list may be single or multi select.
 * <p>
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>SINGLE, MULTI</dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>Selection, DefaultSelection</dd>
 * </dl>
 * <p>
 * Note: Only one of SINGLE and MULTI may be specified.
 * <p>
 * MPORTANT: This class is <i>not</i> intended to be subclassed.
 */
public class List extends Scrollable {
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
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public List(Composite parent, int style) throws SWTException {
    super(parent, style);
  }

  /**
   * Returns the zero-relative indices of the items which are currently
   * selected in the receiver. The order of the indices is unspecified. The
   * array is empty if no items are selected.
   * <p>
   * Note: This is not the actual structure used by the receiver to maintain
   * its selection, so modifying the array will not affect the receiver.
   *
   * @return the array of indices of the selected items
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public int[] getSelectionIndices() throws SWTException {
    checkWidget();

    int indices[] = (int[])callMethod("getSelectionIndices");
    return (indices);
  }

  /**
   * Returns the zero-relative index of the item which is currently at the top
   * of the receiver. This index can change when items are scrolled or new
   * items are added or removed.
   *
   * @return the index of the top item
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public int getTopIndex() throws SWTException {
    checkWidget();
    return (Integer)callMethod("getTopIndex");
  }

  /**
   * Deselects all selected items in the receiver.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void deselectAll() throws SWTException {
    checkWidget();
    callMethod("deselectAll");
  }

  /**
   * Removes all of the items from the receiver.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void removeAll() throws SWTException {
    checkWidget();
    callMethod("removeAll");
  }

  /**
   * Sets the receiver's items to be the given array of items.
   *
   * @param items the array of items
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the items array is null
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *      if an item in the items array is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void setItems(String[] items) throws SWTException {
    if (items == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    for (String item: items) {
      if (item == null) {
        throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
      }
    }

    checkWidget();

    callMethod("setItems", (Object)items);
  }

  /**
   * Sets the zero-relative index of the item which is currently at the top of
   * the receiver. This index can change when items are scrolled or new items
   * are added and removed.
   *
   * @param index the index of the top item
   * @throws SWTException
   *  @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void setTopIndex(int index) throws SWTException {
    checkWidget();
    callMethod("setTopIndex", index);
  }
}
