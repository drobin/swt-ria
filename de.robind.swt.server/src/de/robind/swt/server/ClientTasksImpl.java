package de.robind.swt.server;

import java.lang.reflect.Field;

import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.Key;
import org.eclipse.swt.widgets.Event;
import org.jboss.netty.channel.Channels;

import de.robind.swt.msg.SWTAttrRequest;
import de.robind.swt.msg.SWTAttrResponse;
import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTCallResponse;
import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTException;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.msg.SWTRegRequest;
import de.robind.swt.msg.SWTRegResponse;
import de.robind.swt.msg.SWTResponse;

/**
 * Implementation of the {@link ClientTasks}-interface.
 *
 * @author Robin Doer
 */
public class ClientTasksImpl implements ClientTasks {
  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#createObject(org.eclipse.swt.server.Key, int, java.lang.Class, java.lang.Object[])
   */
  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws Throwable {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTNewRequest request = app.getMessageFactory().createNewRequest(
        id, objClass, normalizeArguments(args));

    Channels.write(app.getChannel(), request);
    SWTResponse response = app.getResponseQueue().take();

    if (response instanceof SWTException) {
      throw ((SWTException)response).getCause();
    }

    if (!(response instanceof SWTNewResponse)) {
      throw new Exception("Illegal response of type " +
          response.getClass().getName() + " received");
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#callMethod(org.eclipse.swt.server.Key, int, java.lang.String, java.lang.Object[])
   */
  public Object callMethod(Key key, int id, String method, Object... args)
      throws Throwable {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTCallRequest request = app.getMessageFactory().createCallRequest(
        id, method, normalizeArguments(args));

    Channels.write(app.getChannel(), request);
    SWTResponse response = app.getResponseQueue().take();

    if (response instanceof SWTException) {
      throw ((SWTException)response).getCause();
    }

    if (!(response instanceof SWTCallResponse)) {
      throw new Exception("Illegal response of type " +
          response.getClass().getName() + " received");
    }

    return (((SWTCallResponse)response).getResult());
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#registerEvent(org.eclipse.swt.server.Key, int, int, boolean)
   */
  public void registerEvent(Key key, int id, int eventType, boolean enable)
      throws Throwable {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTRegRequest request = app.getMessageFactory().createRegRequest(
        id, eventType, enable);

    Channels.write(app.getChannel(), request);
    SWTResponse response = app.getResponseQueue().take();

    if (response instanceof SWTException) {
      throw ((SWTException)response).getCause();
    }

    if (!(response instanceof SWTRegResponse)) {
      throw new Exception("Illegal response of type " +
          response.getClass().getName() + " received");
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#updateAttribute(org.eclipse.swt.server.Key, int, java.lang.String, java.lang.Object)
   */
  public void updateAttribute(Key key, int id, String attrName,
      Object attrValue) throws Throwable {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTAttrRequest request = app.getMessageFactory().createAttrRequest(
        id, attrName, attrValue);

    Channels.write(app.getChannel(), request);
    SWTResponse response = app.getResponseQueue().take();

    if (response instanceof SWTException) {
      throw ((SWTException)response).getCause();
    }

    if (!(response instanceof SWTAttrResponse)) {
      throw new Exception("Illegal response of type " +
          response.getClass().getName() + " received");
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.server.ClientTasks#waitForEvent(org.eclipse.swt.server.Key)
   */
  public Event waitForEvent(Key key) throws Exception, InterruptedException {
    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTEvent swtEvent = app.getEventQueue().take();

    Event event = new Event();
    for (String attribute: swtEvent.getAttributes()) {
      Field f = Event.class.getField(attribute);
      Object value = swtEvent.getAttributeValue(attribute);

      if (value instanceof SWTObjectId) {
        SWTObject obj = SWTObject.findObjectById(((SWTObjectId)value).getId());
        f.set(event, obj);
      } else {
        f.set(event, value);
      }
    }

    return (event);
  }

  /**
   * Normalize the given argument-list.
   * <p>
   * If a {@link SWTObject} is placed into the array, it is replaced by the
   * related {@link SWTObjectId}.
   *
   * @param args Arguments which should be normalized
   * @return Normalized arguments
   */
  private Object[] normalizeArguments(Object args[]) {
    Object result[] = new Object[args.length];

    for (int i = 0; i < args.length; i++) {
      if (args[i] instanceof SWTObject) {
        result[i] = new SWTObjectId(((SWTObject)args[i]).getId());
      } else {
        result[i] = args[i];
      }
    }

    return (result);
  }

  /**
   * Checks the given key.
   * <p>
   * The key cannot be null and must be an instance of
   * {@link SWTApplicationKey}.
   *
   * @param key The key to check
   * @return The value {@link SWTApplicationKey}
   * @throws NullPointerException if <code>key</code> is <code>null</code>
   * @throws IllegalArgumentException if <code>key</code> is not an instance
   *         of {@link SWTApplicationKey}.
   */
  private SWTApplicationKey checkKey(Key key)
      throws NullPointerException, IllegalArgumentException {

    if (key == null) {
      throw new NullPointerException("key cannot be null");
    }

    if (!(key instanceof SWTApplicationKey)) {
      throw new IllegalArgumentException(
          "Unsupported key received. Expected " +
          SWTApplicationKey.class.getName() +
          ", received " + key.getClass().getName());
    }

    return ((SWTApplicationKey)key);
  }
}
