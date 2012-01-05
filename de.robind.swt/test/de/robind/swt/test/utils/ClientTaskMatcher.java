package de.robind.swt.test.utils;

import java.util.Arrays;

import org.eclipse.swt.SWTObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import de.robind.swt.test.utils.TestClientTasks.RequestStore;
import de.robind.swt.test.utils.TestClientTasks.RequestType;

public class ClientTaskMatcher extends TypeSafeMatcher<TestClientTasks> {
  private RequestType type;
  int id;
  private String method = null;
  private Object args[] = null;

  private ClientTaskMatcher(RequestType type, int id, String method, Object args[]) {
    this.type = type;
    this.id = id;
    this.method = method;
    this.args = args;
  }

  /* (non-Javadoc)
   * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
   */
  public void describeTo(Description description) {
    switch (type) {
      case Call:
        description.appendText("A method invocation with methodname ");
        description.appendValue(this.method);
        description.appendText(" and arguments ");
        description.appendValue(Arrays.toString(this.args));
        break;
      default:
        throw new Error("Add description for " + type);
    }
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(TestClientTasks clientTasks) {
    RequestStore store = clientTasks.callRequestQueue.poll();

    if (store == null) {
      return (false);
    }

    if (this.type == RequestType.Call) {
      return store.matches(this.id, this.method, this.args);
    } else {
      throw new Error("add matcher for " + this.type);
    }
  }

  public static Matcher<TestClientTasks> callRequest(SWTObject obj, String method, Object... args) {
    return (new ClientTaskMatcher(RequestType.Call, obj.getId(), method, args));
  }
}
