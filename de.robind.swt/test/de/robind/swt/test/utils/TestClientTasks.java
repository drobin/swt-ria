package de.robind.swt.test.utils;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Event;

public class TestClientTasks implements ClientTasks {
  private Object callMethodResult = null;
  Queue<RequestStore> callRequestQueue = new LinkedList<TestClientTasks.RequestStore>();

  enum RequestType {
    Call
  };

  static class RequestStore {
    RequestType type;
    Key key;
    int id;
    String method;
    Object args[];

    boolean matches(int id, String m, Object args[]) {
      if (this.id != id || !m.equals(this.method)) {
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

    RequestStore store = new RequestStore();
    store.type = RequestType.Call;
    store.key = key;
    store.id = id;
    store.method = method;
    store.args = args;
    this.callRequestQueue.offer(store);

    return (result);
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
