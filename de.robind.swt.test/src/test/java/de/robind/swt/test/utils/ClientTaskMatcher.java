package de.robind.swt.test.utils;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import de.robind.swt.base.SWTObject;
import de.robind.swt.test.utils.TestClientTasks.AttrRequestStore;
import de.robind.swt.test.utils.TestClientTasks.CallRequestStore;
import de.robind.swt.test.utils.TestClientTasks.CreateRequestStore;
import de.robind.swt.test.utils.TestClientTasks.RegisterRequestStore;
import de.robind.swt.test.utils.TestClientTasks.RequestStore;

public class ClientTaskMatcher extends TypeSafeMatcher<TestClientTasks> {
  int id;
  private Object args = null;

  private static class CreateArgs {
    Class<?> objClass;
    Object args[];
  };

  private static class CallArgs {
    String method;
    Object args[];
  };

  private static class RegArgs {
    int eventType;
    boolean enable;
  }

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
    if (this.args instanceof CreateArgs) {
      description.appendText("Create request with class ");
      description.appendValue(((CreateArgs)this.args).objClass);
      description.appendText(" and arguments ");
      description.appendValue(Arrays.toString(((CreateArgs)this.args).args));
    } else if (this.args instanceof CallArgs) {
      description.appendText("A method invocation with methodname ");
      description.appendValue(((CallArgs)this.args).method);
      description.appendText(" and arguments ");

      description.appendValue(Arrays.toString(((CallArgs)this.args).args));
    } else if (this.args instanceof RegArgs) {
      description.appendText("Event registration with event-type ");
      description.appendValue(((RegArgs)this.args).eventType);
      description.appendText(" and enable-state ");
      description.appendValue(((RegArgs)this.args).enable);
    } else if (this.args instanceof AttrArgs) {
      description.appendText("An attribute update of the attribute ");
      description.appendValue(((AttrArgs)this.args).attrName);
      description.appendText(" with the value ");
      description.appendValue(((AttrArgs)this.args).attrValue);
    } else {
      throw new Error("Add description for " + this.args.getClass().getName());
    }
  }

  /* (non-Javadoc)
   * @see org.junit.internal.matchers.TypeSafeMatcher#matchesSafely(java.lang.Object)
   */
  @Override
  public boolean matchesSafely(TestClientTasks clientTasks) {
    RequestStore store;

    if ((store = clientTasks.requestQueue.poll()) == null) {
      return (false);
    }

    if (this.args instanceof CreateArgs && store instanceof CreateRequestStore) {
      CreateRequestStore createStore = (CreateRequestStore)store;
      return (createStore.matches(this.id,
          ((CreateArgs)this.args).objClass, ((CreateArgs)this.args).args));
    } else if (this.args instanceof CallArgs && store instanceof CallRequestStore) {
      CallRequestStore callStore = (CallRequestStore)store;
      return (callStore.matches(this.id,
          ((CallArgs)this.args).method, ((CallArgs)this.args).args));
    } else if (this.args instanceof RegArgs && store instanceof RegisterRequestStore) {
      RegisterRequestStore regStore = (RegisterRequestStore)store;
      return (regStore.matches(this.id,
          ((RegArgs)this.args).eventType, ((RegArgs)this.args).enable));
    } else if (this.args instanceof AttrArgs && store instanceof AttrRequestStore) {
      AttrRequestStore attrStore = (AttrRequestStore)store;
      return (attrStore.matches(id,
          ((AttrArgs)this.args).attrName, ((AttrArgs)this.args).attrValue));
    }

    return (false);
  }

  public static Matcher<TestClientTasks> createRequest(SWTObject obj, Class<?> objClass, Object... args) {
    CreateArgs createArgs = new CreateArgs();
    createArgs.objClass = objClass;
    createArgs.args = args;

    return (new ClientTaskMatcher(obj.getId(), createArgs));
  }

  public static Matcher<TestClientTasks> callRequest(SWTObject obj, String method, Object... args) {
    CallArgs callArgs = new CallArgs();
    callArgs.method = method;
    callArgs.args = args;

    return (new ClientTaskMatcher(obj.getId(), callArgs));
  }

  public static Matcher<TestClientTasks> registerRequest(SWTObject obj, int eventType, boolean enable) {
    RegArgs regArgs = new RegArgs();
    regArgs.eventType = eventType;
    regArgs.enable = enable;

    return (new ClientTaskMatcher(obj.getId(), regArgs));
  }

  public static Matcher<TestClientTasks> attrRequest(SWTObject obj, String attrName, Object attrValue) {
    AttrArgs attrArgs = new AttrArgs();
    attrArgs.attrName = attrName;
    attrArgs.attrValue = attrValue;

    return (new ClientTaskMatcher(obj.getId(), attrArgs));
  }
}
