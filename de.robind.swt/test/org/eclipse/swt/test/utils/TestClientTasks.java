package org.eclipse.swt.test.utils;

import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Event;

public class TestClientTasks implements ClientTasks {
  private Object callMethodResult = null;

  public void setCallMethodResult(Object result) {
    this.callMethodResult = result;
  }

  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws Throwable {
    // TODO Auto-generated method stub

  }

  public Object callMethod(Key key, int id, String method, Object... args)
      throws Throwable {

    Object result = this.callMethodResult;
    this.callMethodResult = null;

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
