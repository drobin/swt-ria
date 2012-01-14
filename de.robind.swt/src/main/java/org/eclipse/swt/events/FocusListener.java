package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated as controls gain and lose focus.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addFocusListener(FocusListener)} method and removed using the
 * {@link Control#removeFocusListener(FocusListener)} method. When a control
 * gains or loses focus, the appropriate method will be invoked.
 */
public interface FocusListener extends TypedListener {
  /**
   * Sent when a control gets focus.
   *
   * @param e an event containing information about the focus change
   */
  void focusGained(FocusEvent e);

  /**
   * Sent when a control loses focus.
   *
   * @param e an event containing information about the focus change
   */
  void focusLost(FocusEvent e);
}
