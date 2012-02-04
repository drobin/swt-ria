package test.de.robind.swt.base.utils;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import org.eclipse.swt.SWTException;

import de.robind.swt.base.ClientTasks;
import de.robind.swt.base.Key;

public class TestClientTasks extends ClientTasks {
  public Queue<MethodInvocation> invocationQueue =
      new LinkedList<TestClientTasks.MethodInvocation>();
  public Object callMethodResult = null;

  public static class MethodInvocation {
    public Key key;

    public MethodInvocation(Key key) {
      this.key = key;
    }
  };

  public static class CreateObjectInvocation extends MethodInvocation {
    public int id;
    public Class<?> objClass;
    public Object args[];

    public CreateObjectInvocation(
        Key key, int id, Class<?> objClass, Object args[]) {
      super(key);

      this.id = id;
      this.objClass = objClass;
      this.args = args;
    }
  }

  public static class CallMethodInvocation extends MethodInvocation {
    public int id;
    public String method;
    public Object args[];

    public CallMethodInvocation(Key key, int id, String method, Object args[]) {
      super(key);

      this.id = id;
      this.method = method;
      this.args = args;
    }
  }

  public static class RegisterEventInvocation extends MethodInvocation {
    public int id;
    public int eventType;
    public boolean enable;

    public RegisterEventInvocation(
        Key key, int id, int eventType, boolean enable) {
      super(key);

      this.id = id;
      this.eventType = eventType;
      this.enable = enable;
    }
  }

  public static class UpdateAttributeInvocation extends MethodInvocation {
    public int id;
    public String attrName;
    public Object attrValue;

    public UpdateAttributeInvocation(
        Key key, int id, String attrName, Object attrValue) {
      super(key);

      this.id = id;
      this.attrName = attrName;
      this.attrValue = attrValue;
    }
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#createObject(de.robind.swt.base.Key, int, java.lang.Class, java.lang.Object[])
   */
  @Override
  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws SWTException {

    this.invocationQueue.offer(
        new CreateObjectInvocation(key, id, objClass, args));
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#callMethod(de.robind.swt.base.Key, int, java.lang.String, java.lang.Object[])
   */
  @Override
  public Object callMethod(Key key, int id, String method, Object... args)
      throws SWTException {

    this.invocationQueue.offer(new CallMethodInvocation(key, id, method, args));

    Object result = this.callMethodResult;
    this.callMethodResult = null;

    return (result);
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#registerEvent(de.robind.swt.base.Key, int, int, boolean)
   */
  @Override
  public void registerEvent(Key key, int id, int eventType, boolean enable)
      throws SWTException {

    this.invocationQueue.offer(
        new RegisterEventInvocation(key, id, eventType, enable));
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#updateAttribute(de.robind.swt.base.Key, int, java.lang.String, java.lang.Object)
   */
  @Override
  public void updateAttribute(Key key, int id, String attrName, Object attrValue)
      throws SWTException {

    this.invocationQueue.offer(
        new UpdateAttributeInvocation(key, id, attrName, attrValue));
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#waitForEvent(de.robind.swt.base.Key)
   */
  @Override
  public Properties waitForEvent(Key key) throws SWTException {
    throw new UnsupportedOperationException("Not implemented");
  }
}
