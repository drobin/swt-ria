package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of a drag gesture.
 */
public class DragDetectEvent extends MouseEvent {
  private static final long serialVersionUID = -3926631981992434907L;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e the untyped event containing the information
   */
  public DragDetectEvent(Event e) {
    super(e);
  }
}
