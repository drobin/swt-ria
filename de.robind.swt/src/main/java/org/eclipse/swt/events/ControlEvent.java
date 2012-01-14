package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of controls being moved or
 * resized.
 */
public class ControlEvent extends TypedEvent {
  private static final long serialVersionUID = -7261427468751275429L;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e the untyped event containing the information
   */
  public ControlEvent(Event e) {
    super(e);
  }
}
