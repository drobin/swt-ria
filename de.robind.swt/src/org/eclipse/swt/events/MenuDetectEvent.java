package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent whenever the platform- specific trigger for
 * showing a context menu is detected.
 */
public class MenuDetectEvent extends TypedEvent {
  private static final long serialVersionUID = -1740654316512421608L;

  /**
   * A flag indicating whether the operation should be allowed. Setting this
   * field to <code>false</code> will cancel the operation.
   */
  public boolean doit;

  /**
   * the display-relative x coordinate of the pointer at the time the context
   * menu trigger occurred
   */
  public int x;

  /**
   * the display-relative y coordinate of the pointer at the time the context
   * menu trigger occurred
   */
  public int y;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e the untyped event containing the information
   */
  public MenuDetectEvent(Event e) {
    super(e);

    this.doit = e.doit;
    this.x = e.x;
    this.y = e.y;
  }
}
