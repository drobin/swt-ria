package test.de.robind.swt.base.utils;

import org.eclipse.swt.SWTException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 * Matcher is used to check the content of a {@link SWTException}.
 *
 * @author Robin Doer
 */
public class SWTExceptionMatcher extends TypeSafeMatcher<SWTException> {
  /**
   * The expected code of the {@link SWTException}
   */
  private int code;

  /**
   * Creates a new matcher.
   * @param code the expected code of the {@link SWTException}
   */
  private SWTExceptionMatcher(int code) {
    this.code = code;
  }

  /* (non-Javadoc)
   * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
   */
  public void describeTo(Description description) {
    description.appendText("SWTException with code ");
    description.appendValue(this.code);
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(SWTException e) {
    return (e.code == this.code);
  }

  /**
   * The {@link SWTException} needs to have the given {@link SWTException#code}.
   *
   * @param code the expected {@link SWTException#code}
   * @return The related matcher
   */
  public static Matcher<SWTException> swtCode(int code) {
    return (new SWTExceptionMatcher(code));
  }
}
