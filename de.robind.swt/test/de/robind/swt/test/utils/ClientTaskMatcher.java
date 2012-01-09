package de.robind.swt.test.utils;

import java.util.Arrays;

import org.eclipse.swt.SWTObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import de.robind.swt.test.utils.TestClientTasks.AttrRequestStore;
import de.robind.swt.test.utils.TestClientTasks.CallRequestStore;

public class ClientTaskMatcher extends TypeSafeMatcher<TestClientTasks> {
  int id;
  private Object args = null;

  private static class CallArgs {
    String method;
    Object args[];
  };

  private static class AttrArgs {
    String attrName;
    Object attrValue;
  }

  private ClientTaskMatcher(int id, Object args) {
    this.id = id;
    this.args = args;
  }

  /* (non-Javadoc)
   * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
   */
  public void describeTo(Description description) {
    if (this.args instanceof CallArgs) {
      description.appendText("A method invocation with methodname ");
      description.appendValue(((CallArgs)this.args).method);
      description.appendText(" and arguments ");

      description.appendValue(Arrays.toString(((CallArgs)this.args).args));
    } else if (this.args instanceof AttrArgs) {
      description.appendText("An attribute update of the attribute ");
      description.appendValue(((AttrArgs)this.args).attrName);
      description.appendText(" with the value ");
      description.appendValue(((AttrArgs)this.args).attrValue);
    }

    throw new Error("Add description for " + this.args.getClass().getName());
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(TestClientTasks clientTasks) {
    if (this.args instanceof CallArgs) {
      CallRequestStore store;

      if ((store = clientTasks.callRequestQueue.poll()) == null) {
        return (false);
      }

      return (store.matches(this.id,
          ((CallArgs)this.args).method, ((CallArgs)this.args).args));
    } else if (this.args instanceof AttrArgs) {
      AttrRequestStore store;

      if ((store = clientTasks.attrRequestQueue.poll()) == null) {
        return (false);
      }

      return (store.matches(id,
          ((AttrArgs)this.args).attrName, ((AttrArgs)this.args).attrValue));
    } else {
      throw new Error("add matcher for " + this.args.getClass().getName());
    }
  }

  public static Matcher<TestClientTasks> callRequest(SWTObject obj, String method, Object... args) {
    CallArgs callArgs = new CallArgs();
    callArgs.method = method;
    callArgs.args = args;

    return (new ClientTaskMatcher(obj.getId(), callArgs));
  }

  public static Matcher<TestClientTasks> attrRequest(SWTObject obj, String attrName, Object attrValue) {
    AttrArgs attrArgs = new AttrArgs();
    attrArgs.attrName = attrName;
    attrArgs.attrValue = attrValue;

    return (new ClientTaskMatcher(obj.getId(), attrArgs));
  }
}
