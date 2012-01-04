package org.eclipse.swt.layout;

import org.eclipse.swt.server.Key;
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
public class FormData extends LayoutData {

  /* (non-Javadoc)
   * @see org.eclipse.swt.layout.LayoutData#createLayoutData(org.eclipse.swt.server.Key)
   */
  @Override
  public void createLayoutData(Key key) throws Throwable {
    // TODO Auto-generated method stub
  }

}
