package org.eclipse.swt.events;

import java.util.EventObject;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

/**
 * This is the super class for all typed event classes provided
 * by SWT. Typed events contain particular information which is
 * applicable to the event occurrence.
 *
 * @see org.eclipse.swt.widgets.Event
 */
public class TypedEvent extends EventObject {
  private static final long serialVersionUID = 190611915670591702L;

  /**
   * the display where the event occurred
   *
   * @since 2.0
   */
  public Display display;

  /**
   * the widget that issued the event
   */
  public Widget widget;

  /**
   * the time that the event occurred.
   *
   * NOTE: This field is an unsigned integer and should
   * be AND'ed with 0xFFFFFFFFL so that it can be treated
   * as a signed long.
   */
  public int time;

  /**
   * a field for application use
   */
  public Object data;

  /**
   * Constructs a new instance of this class based on the
   * information in the argument.
   *
   * @param e the low level event to initialize the receiver with
   */
  public TypedEvent(Event e) {
    super(e.widget);

    this.display = e.display;
    this.widget = e.widget;
    this.time = e.time;
    this.data = e.data;
  }

  /**
   * Constructs a new instance of this class.
   *
   * @param object the object that fired the event
   */
  public TypedEvent(Object object)  {
    super(object);
  }

  /**
   * Returns a string containing a concise, human-readable
   * description of the receiver.
   *
   * @return a string representation of the event
   */
  public String toString() {
    return getClass().getSimpleName() + "[" + widget + " time=" + time +
        " data=" + data + "]";
  }
}
