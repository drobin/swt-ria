package org.eclipse.swt.layout;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DelayedCreation;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.server.Trackable;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

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
public class FormData extends SWTObject implements DelayedCreation {
  /**
   * Arguments passed to the creation-message.
   * @see #createLayout(Key)
   */
  private Object createArguments[] = {};

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
    this.width = width;
    this.height = height;
    this.createArguments = new Object[] {
      this.width,
      this.height
    };
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.DelayedCreation#createObject(org.eclipse.swt.server.Key)
   */
  public void createObject(Key key) throws Throwable {
    ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
    clientTasks.createObject(key, getId(), getClass(), this.createArguments);
  }

  /**
   * Invoked, if an attribute of the class has changed.
   *
   * @param field The name of the attribute-field, which has changed
   */
  public void attributeChanged(String field) {
  }

  /**
   * Sends the update-attribute-request to the client.
   *
   * @param obj The object, which has changed
   * @param field Name of the attribute, which has changed
   * @throws SWTException if the request could not be sent
   */
  private static void changeAttribute(SWTObject obj, String field)
      throws SWTException {

    Object attrValue = getAttributeValue(obj, field);
    Display display = Display.getCurrent();
    display.updateAttribute(obj.getId(), field, attrValue);
  }

  /**
   * Retrieves the value of the attribute with the given field-name.
   *
   * @param obj The object to read out
   * @param field The name of the attribute-field
   * @return The attribute-value
   * @throws SWTException failed to retrieve the value
   */
  private static Object getAttributeValue(Object obj, String field)
      throws SWTException {

    try {
      Field f = obj.getClass().getField(field);
      return (f.get(obj));
    } catch (NoSuchFieldException e) {
      // TODO Here we need the correct code
      SWTException exc = new SWTException(SWT.ERROR_UNSPECIFIED);
      exc.throwable = e;
      throw exc;
    } catch (IllegalAccessException e) {
      // TODO Here we need the correct code
      SWTException exc = new SWTException(SWT.ERROR_UNSPECIFIED);
      exc.throwable = e;
      throw exc;
    }
  }
}
