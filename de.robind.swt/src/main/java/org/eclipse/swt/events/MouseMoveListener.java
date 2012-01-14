package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide a method that deals with the
 * events that are generated as the mouse pointer moves.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addMouseMoveListener(MouseMoveListener)} method and removed
 * using the {@link Control#removeMouseMoveListener(MouseMoveListener)} method.
 * As the mouse moves, the {@link #mouseMove(MouseEvent)} method will be
 * invoked.
 */
public interface MouseMoveListener extends TypedListener {
  /**
   * Sent when the mouse moves.
   *
   * @param e an event containing information about the mouse move
   */
  void mouseMove(MouseEvent e);
}
