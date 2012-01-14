package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of widgets being disposed.
 */
public class DisposeEvent extends TypedEvent {
  private static final long serialVersionUID = 4836600544334182124L;

  /**
   * Constructs a new instance of this class based on the information in the
   * given untyped event.
   *
   * @param e the untyped event containing the information
   */
  public DisposeEvent(Event e) {
    super(e);
  }
}
