package de.robind.swt.protocol.decoder;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 * Matcher used to match against the {@link Throwable#getCause()} of
 * an {@link Throwable exception}.
 *
 * @author Robin Doer
 */
public class CauseMatcher extends TypeSafeMatcher<Throwable>{
  /**
   * The expected cause-class.
   */
  private Class<? extends Throwable> expectedClass = null;

  /**
   * The expected cause-message.
   */
  private String expectedMessage = null;

  /**
   * Creates a new matcher.
   * @param causeClass The expected cause-class
   * @param causeMessage The expected cause-message
   */
  private CauseMatcher(
      Class<? extends Throwable> causeClass, String causeMessage) {

    this.expectedClass = causeClass;
    this.expectedMessage = causeMessage;
  }

  /* (non-Javadoc)
   * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
   */
  public void describeTo(Description description) {
    description.appendText("cause exception");

    if (this.expectedClass != null) {
      description.appendText(" of type ");
      description.appendValue(this.expectedClass);
    }

    if (this.expectedMessage != null) {
      description.appendText(" with message ");
      description.appendValue(this.expectedMessage);
    }
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(Throwable t) {
    if (t.getCause() == null) {
      return (false);
    }

    if (this.expectedClass != null &&
        !this.expectedClass.equals(t.getCause().getClass())) {
      return (false);
    }

    if (this.expectedMessage != null &&
        !this.expectedMessage.equals(t.getCause().getMessage())) {
      return (false);
    }

    return (true);
  }

  /**
   * The {@link Throwable#getCause() cause of the test-object} needs to be
   * of the given type.
   *
   * @param c Class of cause-exception
   * @return The related matcher
   */
  public static Matcher<Throwable> causeClass(Class<? extends Throwable> c) {
    return (new CauseMatcher(c, null));
  }

  /**
   * The {@link Throwable#getCause() cause of the test-object} must have the
   * given message.
   *
   * @param msg Message of cause-exception
   * @return The related matcher
   */
  public static Matcher<Throwable> causeMsg(String msg) {
    return (new CauseMatcher(null, msg));
  }
}
