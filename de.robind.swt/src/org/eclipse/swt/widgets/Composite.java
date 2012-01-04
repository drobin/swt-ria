package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;

/**
 * Instances of this class are controls which are capable of containing other
 * controls.
 * <p>
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>
 *    NO_BACKGROUND, NO_FOCUS, NO_MERGE_PAINTS, NO_REDRAW_RESIZE,
 *    NO_RADIO_GROUP, EMBEDDED, DOUBLE_BUFFERED
 *  </dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>(none)</dd>
 * </dl>
 * <p>
 * Note: The {@link SWT#NO_BACKGROUND}, {@link SWT#NO_FOCUS},
 * {@link SWT#NO_MERGE_PAINTS}, and {@link SWT#NO_REDRAW_RESIZE} styles are
 * intended for use with {@link Canvas}. They can be used with Composite if you
 * are drawing your own, but their behavior is undefined if they are used with
 * subclasses of Composite other than {@link Canvas}.
 * <p>
 * Note: The {@link SWT#CENTER} style, although undefined for composites, has
 * the same value as {@link SWT#EMBEDDED} which is used to embed widgets from
 * other widget toolkits into SWT. On some operating systems (GTK, Motif), this
 * may cause the children of this composite to be obscured.
 * <p>
 * This class may be subclassed by custom control implementors who are building
 * controls that are constructed from aggregates of other controls.
 */
public class Composite extends Scrollable {
  private Layout layout = null;

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
   *               (cannot be null)
   * @param style the style of widget to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the parent is null
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public Composite(Composite parent, int style) throws SWTException {
    super(parent, style);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Widget#checkSubclass()
   */
  @Override
  protected void checkSubclass() throws SWTException {
    // remove check, this class can be subclassed
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Widget#getCreateClass()
   */
  @Override
  protected Class<? extends Widget> getCreateClass() {
    // This class can be subclasses, subclasses are unknown by the client
    // They are always created as a Composite on client-side
    return (Composite.class);
  }

  /**
   * Returns layout which is associated with the receiver, or <code>null</code>
   * if one has not been set.
   *
   * @return the receiver's layout or <code>null</code>
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
  public Layout getLayout() throws SWTException {
    checkWidget();
    return (this.layout);
  }

  /**
   * Sets the layout which is associated with the receiver to be the argument
   * which may be <code>null</code>.
   *
   * @param layout the receiver's new layout or <code>null</code>
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
  public void setLayout(Layout layout) throws SWTException {
    checkWidget();
    // TODO Make sure that layout can be null

    try {
      // Create the layout
      layout.createLayout(getDisplay().getKey());

      // Assign the layout to this object
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.callMethod(getDisplay().getKey(), getId(), "setLayout", layout);
    } catch (Throwable t) {
      // TODO Need a special code?
      SWTException e = new SWTException();
      e.throwable = t;
      throw e;
    }

    this.layout = layout;
  }
}
