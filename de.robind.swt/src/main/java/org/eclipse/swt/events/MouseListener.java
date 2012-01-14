package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated as mouse buttons are pressed.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addMouseListener(MouseListener)} method and removed using the
 * {@link Control#removeMouseListener(MouseListener)} method. When a mouse
 * button is pressed or released, the appropriate method will be invoked.
 */
public interface MouseListener extends TypedListener {
  /**
   * Sent when a mouse button is pressed twice within the (operating system
   * specified) double click period.
   *
   * @param e an event containing information about the mouse double click
   */
  void mouseDoubleClick(MouseEvent e);

  /**
   * Sent when a mouse button is pressed.
   *
   * @param e an event containing information about the mouse button press
   */
  void mouseDown(MouseEvent e);

  /**
   * Sent when a mouse button is released.
   *
   * @param e an event containing information about the mouse button release
   */
  void mouseUp(MouseEvent e);
}
