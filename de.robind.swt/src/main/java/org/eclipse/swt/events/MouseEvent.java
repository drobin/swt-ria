package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent whenever mouse related actions occur.
 * This includes mouse buttons being pressed and released, the mouse pointer
 * being moved and the mouse pointer crossing widget boundaries.
 * <p>
 * Note: The {@link #button} field is an integer that represents the mouse
 * button number. This is not the same as the SWT mask constants
 * <code>BUTTON</code>x.
 */
public class MouseEvent extends TypedEvent {
  private static final long serialVersionUID = 5906467391752875809L;

  /**
   * the button that was pressed or released; 1 for the first button, 2 for th
   * second button, and 3 for the third button, etc.
   */
  public int button;

  /**
   * the number times the mouse has been clicked, as defined by the operating
   * system; 1 for the first click, 2 for the second click and so on.
   */
  public int count;

  /**
   * the state of the keyboard modifier keys and mouse masks at the time the
   * event was generated.
   */
  public int stateMask;

  /**
   * the widget-relative, x coordinate of the pointer at the time the mouse
   * button was pressed or released
   */
  public int x;

  /**
   * the widget-relative, y coordinate of the pointer at the time the mouse
   * button was pressed or released
   */
  public int y;

  public MouseEvent(Event e) {
    super(e);

    this.button = e.button;
    this.count = e.count;
    this.stateMask = e.stateMask;
    this.x = e.x;
    this.y = e.y;
  }
}
