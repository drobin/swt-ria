package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated as keys are pressed on the system keyboard.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the {@link Control#addKeyListener(KeyListener)}
 * method and removed using the {@link Control#removeKeyListener(KeyListener)}
 * method. When a key is pressed or released, the appropriate method will be
 * invoked.
 */
public interface KeyListener extends TypedListener {
  /**
   * Sent when a key is pressed on the system keyboard.
   *
   * @param e an event containing information about the key press
   */
  void keyPressed(KeyEvent e);

  /**
   * Sent when a key is released on the system keyboard.
   *
   * @param e an event containing information about the key release
   */
  void keyReleased(KeyEvent e);
}
