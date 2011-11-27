package org.eclipse.swt.widgets;

import org.eclipse.swt.SWTObject;

import de.robind.swt.msg.SWTNewRequest;

/**
 * TODO Needs to be implemented!!
 */
public abstract class Layout extends SWTObject {
  /**
   * TODO Needs to be implemented!!
   */
  public Layout() {

  }

  /**
   * Creates a {@link SWTNewRequest} for the layout.
   * <p>
   * The layout is not a part of the SWT-object-tree. A layout is assigned to
   * a {@link Composite}-instance. Thats why you don't have an association to
   * the top-level {@link Display} und you are not able to access
   * {@link Display#sendMessage(de.robind.swt.ria.msg.SWTRequest)} directly.
   * <p>
   * That's why the creation-message is send, when
   * {@link Composite#setDisplay(Display)} is called. But the {@link Layout}
   * has to provide the creation-message because {@link Composite} does not
   * know any details about the layout-manager.
   *
   * @return the message that create the layout
   */
  protected abstract SWTNewRequest getNewRequest();
}
