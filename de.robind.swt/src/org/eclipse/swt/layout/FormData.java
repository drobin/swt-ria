package org.eclipse.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.Trackable;
import org.eclipse.swt.widgets.Control;

/**
 * Instances of this class are used to define the attachments of a control in
 * a {@link FormLayout}.
 * <p>
 * To set a {@link FormData} object into a control, you use the
 * {@link Control#setLayoutData(Object)} method. To define attachments for the
 * {@link FormData}, set the fields directly, like this:
 *
 * <pre>
 *  FormData data = new FormData();
 *  data.left = new FormAttachment(0,5);
 *  data.right = new FormAttachment(100,-5);
 *  button.setLayoutData(formData);
 * </pre>
 *
 * {@link FormData} contains the {@link FormAttachment}s for each edge of the
 * control that the {@link FormLayout} uses to determine the size and position
 * of the control. FormData objects also allow you to set the width and height
 * of controls within a {@link FormLayout}.
 */
public class FormData extends SWTObject {
  /**
   * left specifies the attachment of the left side of the control.
   */
  @Trackable
  public FormAttachment left;

  /**
   * right specifies the attachment of the right side of the control.
   */
  @Trackable
  public FormAttachment right;

  /**
   * top specifies the attachment of the top of the control.
   */
  @Trackable
  public FormAttachment top;

  /**
   * bottom specifies the attachment of the bottom of the control.
   */
  @Trackable
  public FormAttachment bottom;

  /**
   * width specifies the preferred width in pixels. This value is the wHint
   * passed into Control.computeSize(int, int, boolean) to determine the
   * preferred size of the control. The default value is SWT.DEFAULT.
   */
  @Trackable
  public int width = SWT.DEFAULT;

  /**
   * height specifies the preferred height in pixels. This value is the hHint
   * passed into Control.computeSize(int, int, boolean) to determine the
   * preferred size of the control. The default value is SWT.DEFAULT.
   */
  @Trackable
  public int height = SWT.DEFAULT;

  /**
   * Constructs a new instance of FormData using default values.
   */
  public FormData() {
    createObject();
  }

  /**
   * Constructs a new instance of FormData according to the parameters. A value
   * of SWT.DEFAULT indicates that no minimum width or no minimum height is
   * specified.
   *
   * @param width a minimum width for the control
   * @param height a minimum height for the control
   */
  public FormData(int width, int height) {
    createObject(width, height);

    this.width = width;
    this.height = height;
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
