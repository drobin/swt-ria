package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of help being requested for a
 * widget.
 */
public class HelpEvent extends TypedEvent {
  private static final long serialVersionUID = 1228114257668434901L;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e the untyped event containing the information
   */
  public HelpEvent(Event e) {
    super(e);
  }
}
