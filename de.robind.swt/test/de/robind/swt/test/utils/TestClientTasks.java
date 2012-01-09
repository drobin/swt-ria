package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Event;

public class TestClientTasks implements ClientTasks {
  private Object callMethodResult = null;
  Queue<CallRequestStore> callRequestQueue = new LinkedList<TestClientTasks.CallRequestStore>();
  Queue<AttrRequestStore> attrRequestQueue = new LinkedList<TestClientTasks.AttrRequestStore>();

  static abstract class AbstractRequestStore {
    Key key;
    int id;

    protected boolean matches(int id) {
      if (this.id != id) {
        return (false);
      }

      return (true);
    }
  }

  static class CallRequestStore extends AbstractRequestStore {
    String method;
    Object args[];

    boolean matches(int id, String m, Object args[]) {
      if (!super.matches(id)) {
        return (false);
      }

      if (!m.equals(this.method)) {
        return (false);
      }

      if (this.args.length != args.length) {
        return (false);
      }

      for (int i = 0; i < this.args.length; i++) {
        if (this.args[i] != args[i]) {
          return (false);
        }
      }

      return (true);
    }
  }

  static class AttrRequestStore extends AbstractRequestStore {
    String attrName;
    Object attrValue;

    boolean matches(int id, String attrName, Object attrValue) {
      if (!super.matches(id)) {
        return (false);
      }

      if (!attrName.equals(this.attrName)) {
        return (false);
      }

      if (!attrValue.equals(this.attrValue)) {
        return (false);
      }

      return (true);
    }
  }
  public void setCallMethodResult(Object result) {
    this.callMethodResult = result;
  }

  public void clearState() {
    this.callMethodResult = null;
    this.callRequestQueue.clear();
  }

  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws Throwable {
    // TODO Auto-generated method stub

  }

  public Object callMethod(Key key, int id, String method, Object... args)
      throws Throwable {

    Object result = this.callMethodResult;
    this.callMethodResult = null;

    CallRequestStore store = new CallRequestStore();
    store.key = key;
    store.id = id;
    store.method = method;
    store.args = args;
    this.callRequestQueue.offer(store);

    return (result);
  }

  public void updateAttribute(Key key, int id, String attrName, Object attrValue)
      throws Throwable {

    AttrRequestStore store = new AttrRequestStore();
    store.key = key;
    store.id = id;
    store.attrName = attrName;
    store.attrValue = attrValue;
    this.attrRequestQueue.offer(store);
  }

  public void registerEvent(Key key, int id, int eventType, boolean enable)
      throws Throwable {
    // TODO Auto-generated method stub

  }

  public Event waitForEvent(Key key) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}
