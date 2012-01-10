package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

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
   * If the receiver has a layout, asks the layout to lay out (that is, set
   * the size and location of) the receiver's children. If the receiver does
   * not have a layout, do nothing.
   * <p>
   * This is equivalent to calling {@link #layout(true)}.
   * <p>
   * Note: Layout is different from painting. If a child is moved or resized
   * such that an area in the parent is exposed, then the parent will paint.
   * If no child is affected, the parent will not paint.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   * @see #layout(boolean)
   */
  public void layout() throws SWTException {
    checkWidget();
    layout(true);
  }

  /**
   * If the receiver has a layout, asks the layout to lay out (that is, set
   * the size and location of) the receiver's children. If the argument is
   * <code>true</code> the layout must not rely on any information it has
   * cached about the immediate children. If it is <code>false</code> the
   * layout may (potentially) optimize the work it is doing by assuming that
   * none of the receiver's children has changed state since the last layout.
   * If the receiver does not have a layout, do nothing.
   * <p>
   * If a child is resized as a result of a call to layout, the resize event
   * will invoke the layout of the child. The layout will cascade down through
   * all child widgets in the receiver's widget tree until a child is
   * encountered that does not resize. Note that a layout due to a resize will
   * not flush any cached information (same as {@link #layout(false)}).
   * <p>
   * Note: Layout is different from painting. If a child is moved or resized
   * such that an area in the parent is exposed, then the parent will paint. If
   * no child is affected, the parent will not paint.
   *
   * @param changed <code>true</code> if the layout must flush its caches, and
   *                <code>false</code> otherwise.
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
  public void layout(boolean changed) throws SWTException {
    checkWidget();
    layout(changed, false);
  }

  /**
   * If the receiver has a layout, asks the layout to lay out (that is, set the
   * size and location of) the receiver's children. If the changed argument is
   * <code>true</code> the layout must not rely on any information it has
   * cached about its children. If it is <code>false</code> the layout may
   * (potentially) optimize the work it is doing by assuming that none of the
   * receiver's children has changed state since the last layout. If the all
   * argument is <code>true</code> the layout will cascade down through all
   * child widgets in the receiver's widget tree, regardless of whether the
   * child has changed size. The changed argument is applied to all layouts. If
   * the all argument is <code>false</code>, the layout will not cascade down
   * through all child widgets in the receiver's widget tree. However, if a
   * child is resized as a result of a call to layout, the resize event will
   * invoke the layout of the child. Note that a layout due to a resize will
   * not flush any cached information (same as {@link #layout(false)}).
   * <p>
   * Note: Layout is different from painting. If a child is moved or resized
   * such that an area in the parent is exposed, then the parent will paint. If
   * no child is affected, the parent will not paint.
   *
   * @param changed <code>true</code> if the layout must flush its caches, and
   *                <code>false</code> otherwise
   * @param all <code>true</code> if all children in the receiver's widget tree
   *            should be laid out, and <code>false</code> otherwise
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
  public void layout(boolean changed, boolean all) throws SWTException {
    checkWidget();

    callMethod("layout", changed, all);
  }

  /**
   * Forces a lay out (that is, sets the size and location) of all widgets that
   * are in the parent hierarchy of the changed control up to and including the
   * receiver. The layouts in the hierarchy must not rely on any information
   * cached about the changed control or any of its ancestors. The layout may
   * (potentially) optimize the work it is doing by assuming that none of the
   * peers of the changed control have changed state since the last layout. If
   * an ancestor does not have a layout, skip it.
   * <p>
   * Note: Layout is different from painting. If a child is moved or resized
   * such that an area in the parent is exposed, then the parent will paint. If
   * no child is affected, the parent will not paint.
   *
   * @param changed a control that has had a state change which requires a
   *                recalculation of its size
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *      if the changed array is null any of its controls are null or have
   *      been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_PARENT} -
   *      if any control in changed is not in the widget tree of the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void layout(Control[] changed) throws SWTException {
    checkWidget();
    layout(changed, SWT.NONE);
  }

  /**
   * Forces a lay out (that is, sets the size and location) of all widgets that
   * are in the parent hierarchy of the changed control up to and including the
   * receiver.
   * <p>
   * The parameter <code>flags</code> may be a combination of:
   *
   * <dl>
   *  <dt><b>{@link SWT#ALL}</b></dt>
   *  <dd>all children in the receiver's widget tree should be laid out</dd>
   *  <dt><b>{@link SWT#CHANGED}</b></dt>
   *  <dd>the layout must flush its caches</dd>
   *  <dt><b>{@link SWT#DEFER}</b></dt>
   *  <dd>layout will be deferred</dd>
   * </dl>
   *
   * When the changed array is specified, the flags {@link SWT#ALL} and
   * {@link SWT#CHANGED} have no effect. In this case, the layouts in the
   * hierarchy must not rely on any information cached about the changed
   * control or any of its ancestors. The layout may (potentially) optimize the
   * work it is doing by assuming that none of the peers of the changed control
   * have changed state since the last layout. If an ancestor does not have a
   * layout, skip it.
   * <p>
   * When the <code>changed</code> array is not specified, the flag
   * {@link SWT#ALL} indicates that the whole widget tree should be laid out.
   * And the flag {@link SWT#CHANGED} indicates that the layouts should flush
   * any cached information for all controls that are laid out.
   * <p>
   * The {@link SWT#DEFER} flag always causes the layout to be deferred by
   * calling <code>Composite.setLayoutDeferred(true)</code> and scheduling a
   * call to <code>Composite.setLayoutDeferred(false)</code>, which will happen
   * when appropriate (usually before the next event is handled). When this
   * flag is set, the application should not call
   * <code>Composite.setLayoutDeferred(boolean)</code>.
   * <p>
   * Note: Layout is different from painting. If a child is moved or resized
   * such that an area in the parent is exposed, then the parent will paint.
   * If no child is affected, the parent will not paint.
   *
   * @param changed a control that has had a state change which requires a
   *                recalculation of its size
   * @param flags the flags specifying how the layout should happen
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *      if the changed array is null any of its controls are null or have
   *      been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_PARENT} -
   *      if any control in changed is not in the widget tree of the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void layout(Control[] changed, int flags) throws SWTException {
    checkWidget();

    if (changed == null) {
      throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
    }

    for (Control control: changed) {
      if (control == null) {
        throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
      }

      if (control.isDisposed()) {
        throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
      }

      Widget composite = control.getParent();
      boolean detected = false;
      while (composite != null) {
        if (composite == this) {
          detected = true;
          break;
        }

        composite = composite.getParent();
      }

      if (!detected) {
        throw new SWTException(SWT.ERROR_INVALID_PARENT);
      }
    }

    callMethod("layout", changed, flags);
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

    // Assign the key to the layout, so it becomes a part of the object-tree
    layout.setKey(getKey());

    callMethod("setLayout", layout);
    this.layout = layout;
  }
}
