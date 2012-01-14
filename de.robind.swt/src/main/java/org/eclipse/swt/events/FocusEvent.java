package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of widgets gaining and losing
 * focus.
 */
public class FocusEvent extends TypedEvent {
  private static final long serialVersionUID = 7071369172096747735L;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e the untyped event containing the information
   */
  public FocusEvent(Event e) {
    super(e);
  }
}
