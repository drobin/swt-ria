package org.eclipse.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.server.Trackable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.LayoutAdapter;
import org.eclipse.swt.widgets.Shell;

/**
 * {@link FillLayout} is the simplest layout class. It lays out controls in a
 * single row or column, forcing them to be the same size.
 * <p>
 * Initially, the controls will all be as tall as the tallest control, and as
 * wide as the widest. FillLayout does not wrap, but you can specify margins
 * and spacing. You might use it to lay out buttons in a task bar or tool bar,
 * or to stack checkboxes in a Group. FillLayout can also be used when a
 * {@link Composite} only has one child. For example, if a {@link Shell} has a
 * single Group child, FillLayout will cause the Group to completely fill the
 * {@link Shell} (if margins are 0).
 * <p>
 * Example code: first a {@link FillLayout} is created and its type field is
 * set, and then the layout is set into the {@link Composite}. Note that in a
 * {@link FillLayout}, children are always the same size, and they fill all
 * available space.
 *
 * <pre>
 *  FillLayout fillLayout = new FillLayout();
 *  fillLayout.type = SWT.VERTICAL;
 *  shell.setLayout(fillLayout);
 * </pre>
 */
public class FillLayout extends LayoutAdapter {
  /**
   * type specifies how controls will be positioned within the layout. The
   * default value is {@link SWT#HORIZONTAL}. Possible values are:
   *
   *  <ul>
   *    <li>{@link SWT#HORIZONTAL}:
   *      Position the controls horizontally from left to right
   *    </li>
   *    <li>{@link SWT#VERTICAL}:
   *      Position the controls vertically from top to bottom
   *    </li>
   *  </ul>
   */
  @Trackable
  public int type;

  /**
   * marginWidth specifies the number of pixels of horizontal margin that will
   * be placed along the left and right edges of the layout. The default value
   * is 0.
   */
  @Trackable
  public int marginWidth;

  /**
   * marginHeight specifies the number of pixels of vertical margin that will
   * be placed along the top and bottom edges of the layout. The default value
   * is 0.
   */
  @Trackable
  public int marginHeight;

  /**
   * spacing specifies the number of pixels between the edge of one cell and
   * the edge of its neighbouring cell. The default value is 0.
   */
  @Trackable
  public int spacing;

  /**
   * Constructs a new instance of this class.
   */
  public FillLayout() {
    this(SWT.HORIZONTAL);
  }

  /**
   * Constructs a new instance of this class given the type.
   * @param type the type of fill layout
   */
  public FillLayout(int type) {
    createObject(type);

    this.type = type;
  }

  /**
   * Invoked, if an attribute of the class has changed.
   *
   * @param field The name of the attribute-field, which has changed
   * @throws SWTException if the attribute was not updated at the client
   */
  public void attributeChanged(String field) throws SWTException {
    updateAttribute(field);
  }
}
