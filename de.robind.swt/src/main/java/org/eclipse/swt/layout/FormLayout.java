package org.eclipse.swt.layout;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.server.Trackable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.LayoutAdapter;
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
public class FormLayout extends LayoutAdapter {
  /**
   * marginLeft specifies the number of pixels of horizontal margin that will
   * be placed along the left edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginLeft;

  /**
   * marginRight specifies the number of pixels of horizontal margin that will
   * be placed along the right edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginRight;

  /**
   * marginTop specifies the number of pixels of vertical margin that will be
   * placed along the top edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginTop;

  /**
   * marginBottom specifies the number of pixels of vertical margin that will
   * be placed along the bottom edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginBottom;

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
   * spacing specifies the number of pixels between the edge of one control and
   * the edge of its neighbouring control. The default value is 0.
   */
  @Trackable
  public int spacing;

  /**
   * Constructs a new instance of this class.
   */
  public FormLayout() {
    createObject();
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
