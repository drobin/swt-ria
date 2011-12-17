package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide a method that deals with the
 * event that is generated as the mouse wheel is scrolled.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addMouseWheelListener(MouseWheelListener)} method and removed
 * using the {@link Control#removeMouseMoveListener(MouseMoveListener)} method.
 * When the mouse wheel is scrolled the {@link #mouseScrolled(MouseEvent)}
 * method will be invoked.
 */
public interface MouseWheelListener extends TypedListener {
  /**
   * Sent when the mouse wheel is scrolled.
   *
   * @param e an event containing information about the mouse wheel action
   */
  void mouseScrolled(MouseEvent e);
}
