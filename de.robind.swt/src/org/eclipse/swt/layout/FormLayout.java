package org.eclipse.swt.layout;

import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

/**
 * Instances of this class control the position and size of the children of a
 * composite control by using {@link FormAttachment}s to optionally configure
 * the left, top, right and bottom edges of each child.
 * <p>
 * The following example code creates a {@link FormLayout} and then sets it
 * into a {@link Shell}:
 *
 * <pre>
 *  Display display = new Display ();
 *  Shell shell = new Shell(display);
 *  FormLayout layout = new FormLayout();
 *  layout.marginWidth = 3;
 *  layout.marginHeight = 3;
 *  shell.setLayout(layout);
 * </pre>
 *
 * To use a {@link FormLayout}, create a {@link FormData} with
 * {@link FormAttachment} for each child of {@link Composite}. The following
 * example code attaches <tt>button1</tt> to the top and left edge of the
 * composite and <tt>button2</tt> to the right edge of <tt>button1</tt> and the
 * top and right edges of the composite:
 *
 * <pre>
 *  FormData data1 = new FormData();
 *  data1.left = new FormAttachment(0, 0);
 *  data1.top = new FormAttachment(0, 0);
 *  button1.setLayoutData(data1);
 *  FormData data2 = new FormData();
 *  data2.left = new FormAttachment(button1);
 *  data2.top = new FormAttachment(0, 0);
 *  data2.right = new FormAttachment(100, 0);
 *  button2.setLayoutData(data2);
 * </pre>
 *
 * Each side of a child control can be attached to a position in the parent
 * composite, or to other controls within the {@link Composite} by creating
 * nstances of {@link FormAttachment} and setting them into the top, bottom,
 * left, and right fields of the child's {@link FormData}.
 * <p>
 * If a side is not given an attachment, it is defined as not being attached
 * to anything, causing the child to remain at its preferred size. If a child
 * is given no attachment on either the left or the right or top or bottom, it
 * is automatically attached to the left and top of the composite respectively.
 * The following code positions <tt>button1</tt> and <tt>button2</tt> but
 * relies on default attachments:
 *
 * <pre>
 *  FormData data2 = new FormData();
 *  data2.left = new FormAttachment(button1);
 *  data2.right = new FormAttachment(100, 0);
 *  button2.setLayoutData(data2);
 * </pre>
 *
 * IMPORTANT: Do not define circular attachments. For example, do not attach
 * the right edge of <tt>button1</tt> to the left edge of <tt>button2</tt> and
 * then attach the left edge of <tt>button2</tt> to the right edge of
 * <tt>button1</tt>. This will over constrain the layout, causing undefined
 * behavior. The algorithm will terminate, but the results are undefined.
 */
public class FormLayout extends Layout {
  /**
   * Constructs a new instance of this class.
   */
  public FormLayout() {

  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Layout#createLayout(org.eclipse.swt.server.Key)
   */
  @Override
  protected void createLayout(Key key) throws Throwable {
    // TODO Auto-generated method stub

  }
}