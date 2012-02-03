package test.de.robind.swt.base.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import de.robind.swt.base.SWTBaseException;
import de.robind.swt.base.SWTBaseException.Reason;

/**
 * Matcher is used to check the content of a {@link SWTBaseException}.
 *
 * @author Robin Doer
 */
public class SWTBaseExceptionMatcher extends TypeSafeMatcher<SWTBaseException> {
  /**
   * The expected reason of the {@link SWTBaseException}.
   */
  private Reason reason;

  /**
   * Creates a new matcher.
   *
   * @param reason the expected reason of the {@link SWTBaseException}
   */
  private SWTBaseExceptionMatcher(Reason reason) {
    this.reason = reason;
  }

  /* (non-Javadoc)
   * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
   */
  public void describeTo(Description description) {
    description.appendText("SWTBaseException with reason ");
    description.appendValue(this.reason);
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(SWTBaseException e) {
    return (e.getReason() == this.reason);
  }

  /**
   * The {@link SWTBaseException} needs to have the given {@link Reason}.
   *
   * @param reason the expected {@link Reason}
   * @return The related matcher
   */
  public static Matcher<SWTBaseException> reason(Reason reason) {
    return (new SWTBaseExceptionMatcher(reason));
  }
}
