package org.eclipse.swt.test;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 * Matcher is used to check the content of a {@link TypedEvent}.
 *
 * @author Robin Doer
 */
public class TypedEventMatcher extends TypeSafeMatcher<TypedEvent> {
  /**
   * The expected {@link TypedEvent#display}
   */
  private Display display = null;

  /**
   * The expected {@link TypedEvent#widget}
   */
  private Widget widget = null;

  /**
   * The expected {@link TypedEvent#data}
   */
  private Object data = null;

  /**
   * Creates a new {@link TypedEventMatcher}-instance.
   *
   * @param display The expected display assigned to the event
   * @param widget The expected widget assigned to the event
   * @param data The expected data assigned to the event
   */
  private TypedEventMatcher(Display display, Widget widget, Object data) {
    this.display = display;
    this.widget = widget;
    this.data = data;
  }

  /* (non-Javadoc)
   * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
   */
  public void describeTo(Description description) {
    description.appendText("Event with display ");
    description.appendValue(this.display);
    description.appendText(" and widget ");
    description.appendValue(this.widget);
    description.appendText(" and data ");
    description.appendValue(this.data);
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(TypedEvent event) {
    return (event.display == this.display &&
        event.widget == this.widget &&
        event.time == 0 &&
        event.data == data);
  }

  /**
   * The tested {@link TypedEvent} needs to match against the given display
   * and widget.
   *
   * @param display The display which needs to be assigned to the event
   * @param widget The widget which needs to be assigned to the event
   * @return
   */
  public static Matcher<TypedEvent> event(Display display, Widget widget) {
    return (new TypedEventMatcher(display, widget, null));
  }

  /**
   * The tested {@link TypedEvent} needs to match against the given display,
   * widget and data
   *
   * @param display The display which needs to be assigned to the event
   * @param widget The widget which needs to be assigned to the event
   * @param data The data which needs to be assigned to the event
   * @return
   */
  public static Matcher<TypedEvent> event(
      Display display, Widget widget, Object data) {

    return (new TypedEventMatcher(display, widget, data));
  }
}
