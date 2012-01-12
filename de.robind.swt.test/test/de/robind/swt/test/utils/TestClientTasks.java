package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Event;

public class TestClientTasks implements ClientTasks {
  private Object callMethodResult = null;
  Queue<RequestStore> requestQueue = new LinkedList<TestClientTasks.RequestStore>();

  static abstract class RequestStore {
    Key key;
    int id;

    protected boolean matches(int id) {
      if (this.id != id) {
        return (false);
      }

      return (true);
    }

    protected static boolean matchArray(Object a1[], Object a2[]) {
      if (a1.length != a2.length) {
        return (false);
      }

      for (int i = 0; i < a1.length; i++) {
        if (a1[i] != null) {
          if (a1[i].getClass().isArray()) {
            if (!matchArray((Object[])a1[i], (Object[])a2[i])) {
              return (false);
            }
          } else if (!a1[i].equals(a2[i])) {
            return (false);
          }
        } else {
          if (a1[i] != a2[i]) {
            return (false);
          }
        }
      }

      return (true);
    }
  }

  static class CreateRequestStore extends RequestStore {
    Class<?> objClass;
    Object args[];

    boolean matches(int id, Class<?> c, Object args[]) {
      if (!super.matches(id)) {
        return (false);
      }

      if (!c.equals(this.objClass)) {
        return (false);
      }

      if (!RequestStore.matchArray(this.args, args)) {
        return (false);
      }

      return (true);
    }
  }

  static class CallRequestStore extends RequestStore {
    String method;
    Object args[];

    boolean matches(int id, String m, Object args[]) {
      if (!super.matches(id)) {
        return (false);
      }

      if (!m.equals(this.method)) {
        return (false);
      }

      if (!RequestStore.matchArray(this.args, args)) {
        return (false);
      }

      return (true);
    }
  }

  static class RegisterRequestStore extends RequestStore {
    int eventType;
    boolean enable;

    boolean matches(int id, int eventType, boolean enable) {
      if (!super.matches(id)) {
        return (false);
      }

      if (this.eventType != eventType) {
        return (false);
      }

      if (this.enable != enable) {
        return (false);
      }

      return (true);
    }
  }

  static class AttrRequestStore extends RequestStore {
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
    this.requestQueue.clear();
  }

  public int getQueueSize() {
    return (this.requestQueue.size());
  }

  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws Throwable {

    CreateRequestStore store = new CreateRequestStore();
    store.key = key;
    store.id = id;
    store.objClass = objClass;
    store.args = args;
    this.requestQueue.offer(store);
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
    this.requestQueue.offer(store);

    return (result);
  }

  public void updateAttribute(Key key, int id, String attrName, Object attrValue)
      throws Throwable {

    AttrRequestStore store = new AttrRequestStore();
    store.key = key;
    store.id = id;
    store.attrName = attrName;
    store.attrValue = attrValue;
    this.requestQueue.offer(store);
  }

  public void registerEvent(Key key, int id, int eventType, boolean enable)
      throws Throwable {

    RegisterRequestStore store = new RegisterRequestStore();
    store.key = key;
    store.id = id;
    store.eventType = eventType;
    store.enable = enable;
    this.requestQueue.offer(store);
  }

  public Event waitForEvent(Key key) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}
