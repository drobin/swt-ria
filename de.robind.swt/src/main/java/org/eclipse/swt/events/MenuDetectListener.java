package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated when the platform-specific trigger for showing a
 * context menu is detected.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control or TrayItem using the
 * {@link Control#addMenuDetectListener(MenuDetectListener)} method and removed
 * using the {@link Control#removeMenuDetectListener(MenuDetectListener)}
 * method. When the context menu trigger occurs, the
 * {@link #menuDetected(MenuDetectEvent)} method will be invoked.
 */
public interface MenuDetectListener extends TypedListener {
  /**
   * Sent when the platform-dependent trigger for showing a menu item is
   * detected.
   *
   * @param e an event containing information about the menu detect
   */
  void menuDetected(MenuDetectEvent e);
}
