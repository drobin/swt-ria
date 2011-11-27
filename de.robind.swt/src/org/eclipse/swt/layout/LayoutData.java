package org.eclipse.swt.layout;

import org.eclipse.swt.SWTObject;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import de.robind.swt.msg.SWTNewRequest;

/**
 * Data which can be assiged to {@link Control#setLayoutData(Object)}.
 *
 * @author Robin Doer
 */
public abstract class LayoutData extends SWTObject {
  /**
   * Creates a {@link SWTNewRequest} for the layout-data.
   * <p>
   * The layout-data is not a part of the SWT-object-tree. Such an instance is
   * assigned to a {@link Control}. Thats why you don't have an association to
   * the top-level {@link Display} und you are not able to access
   * {@link Display#sendMessage(de.robind.swt.ria.msg.SWTRequest)} directly.
   * <p>
   * That's why the creation-message is send, when
   * {@link Control#setLayoutData(Object)} is called. But the
   * {@link LayoutData} has to provide the creation-message because
   * {@link Control} does not know any details about the assigned layout-data.
   *
   * @return the message that create the layout-data
   */
  public abstract SWTNewRequest getNewRequest();
}
