package de.robind.swt.test.utils;

import org.eclipse.swt.SWTError;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 * Matcher is used to check the content of a {@link SWTError}.
 *
 * @author Robin Doer
 */
public class SWTErrorMatcher extends TypeSafeMatcher<SWTError> {
  /**
   * The expected code of the {@link SWTError}
   */
  private int code;

  /**
   * Creates a new matcher.
   * @param code the expected code of the {@link SWTError}
   */
  private SWTErrorMatcher(int code) {
    this.code = code;
  }

  /* (non-Javadoc)
   * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
   */
  public void describeTo(Description description) {
    description.appendText("SWTError with code ");
    description.appendValue(this.code);
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(SWTError e) {
    return (e.code == this.code);
  }

  /**
   * The {@link SWTError} needs to have the given {@link SWTError#code}.
   *
   * @param code the expected {@link SWTError#code}
   * @return The related matcher
   */
  public static Matcher<SWTError> errorCode(int code) {
    return (new SWTErrorMatcher(code));
  }
}
