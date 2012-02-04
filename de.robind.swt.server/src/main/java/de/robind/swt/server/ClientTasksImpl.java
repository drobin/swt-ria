package de.robind.swt.server;

import java.util.Properties;

import org.eclipse.swt.SWT;
import org.jboss.netty.channel.Channels;

import de.robind.swt.base.ClientTasks;
import de.robind.swt.base.Key;
import de.robind.swt.base.SWTObject;
import de.robind.swt.base.SWTObjectPool;
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
public class ClientTasksImpl extends ClientTasks {
  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#createObject(de.robind.swt.base.Key, int, java.lang.Class, java.lang.Object[])
   */
  public void createObject(Key key, int id, Class<?> objClass, Object... args)
      throws org.eclipse.swt.SWTException {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTNewRequest request = app.getMessageFactory().createNewRequest(
        id, objClass, normalizeArguments(args));

    Channels.write(app.getChannel(), request);
    SWTResponse response = waitForResponse(app);

    if (response instanceof SWTException) {
      org.eclipse.swt.SWTException e =
          new org.eclipse.swt.SWTException(SWT.ERROR_FAILED_EXEC);
      e.throwable = ((SWTException)response).getCause();
      throw e;
    }

    if (!(response instanceof SWTNewResponse)) {
      throw new org.eclipse.swt.SWTException(SWT.ERROR_INVALID_ARGUMENT,
          "Illegal response of type " +
          response.getClass().getName() + " received");
    }
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#callMethod(de.robind.swt.base.Key, int, java.lang.String, java.lang.Object[])
   */
  public Object callMethod(Key key, int id, String method, Object... args)
      throws org.eclipse.swt.SWTException {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTCallRequest request = app.getMessageFactory().createCallRequest(
        id, method, normalizeArguments(args));

    Channels.write(app.getChannel(), request);
    SWTResponse response = waitForResponse(app);

    if (response instanceof SWTException) {
      org.eclipse.swt.SWTException e =
          new org.eclipse.swt.SWTException(SWT.ERROR_FAILED_EXEC);
      e.throwable = ((SWTException)response).getCause();
      throw e;
    }

    if (!(response instanceof SWTCallResponse)) {
      throw new org.eclipse.swt.SWTException(SWT.ERROR_INVALID_ARGUMENT,
          "Illegal response of type " +
          response.getClass().getName() + " received");
    }

    return (((SWTCallResponse)response).getResult());
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#registerEvent(de.robind.swt.base.Key, int, int, boolean)
   */
  public void registerEvent(Key key, int id, int eventType, boolean enable)
      throws org.eclipse.swt.SWTException {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTRegRequest request = app.getMessageFactory().createRegRequest(
        id, eventType, enable);

    Channels.write(app.getChannel(), request);
    SWTResponse response = waitForResponse(app);

    if (response instanceof SWTException) {
      org.eclipse.swt.SWTException e =
          new org.eclipse.swt.SWTException(SWT.ERROR_FAILED_EXEC);
      e.throwable = ((SWTException)response).getCause();
      throw e;
    }

    if (!(response instanceof SWTRegResponse)) {
      throw new org.eclipse.swt.SWTException(SWT.ERROR_INVALID_ARGUMENT,
          "Illegal response of type " +
          response.getClass().getName() + " received");
    }
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#updateAttribute(de.robind.swt.base.Key, int, java.lang.String, java.lang.Object)
   */
  public void updateAttribute(Key key, int id, String attrName,
      Object attrValue) throws org.eclipse.swt.SWTException {

    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTAttrRequest request = app.getMessageFactory().createAttrRequest(
        id, attrName, normalizeArgument(attrValue));

    Channels.write(app.getChannel(), request);
    SWTResponse response = waitForResponse(app);

    if (response instanceof SWTException) {
      org.eclipse.swt.SWTException e =
          new org.eclipse.swt.SWTException(SWT.ERROR_FAILED_EXEC);
      e.throwable = ((SWTException)response).getCause();
      throw e;
    }

    if (!(response instanceof SWTAttrResponse)) {
      throw new org.eclipse.swt.SWTException(SWT.ERROR_INVALID_ARGUMENT,
          "Illegal response of type " +
          response.getClass().getName() + " received");
    }
  }

  /* (non-Javadoc)
   * @see de.robind.swt.base.ClientTasks#waitForEvent(de.robind.swt.base.Key)
   */
  public Properties waitForEvent(Key key) throws org.eclipse.swt.SWTException {
    SWTApplicationKey appKey = checkKey(key);
    SWTApplication app = appKey.getApplication();
    SWTEvent swtEvent = waitForEvent(app);

    Properties eventProperties = new Properties();
    for (String attribute: swtEvent.getAttributes()) {
      Object value = swtEvent.getAttributeValue(attribute);

      if (value instanceof SWTObjectId) {
        SWTObjectPool pool = SWTObjectPool.getInstance();
        SWTObject obj = pool.findObjectById(((SWTObjectId)value).getId());
        eventProperties.put(attribute, obj);
      } else {
        eventProperties.put(attribute, value);
      }
    }

    return (eventProperties);
  }

  /**
   * Waits for a response-message.
   * <p>
   * An {@link InterruptedException} is wrapped into a
   * {@link org.eclipse.swt.SWTException}
   *
   * @param app The source application
   * @return the next response
   * @throws org.eclipse.swt.SWTException if the current thread was interrupted
   */
  private SWTResponse waitForResponse(SWTApplication app)
      throws org.eclipse.swt.SWTException {

    try {
      return (app.getResponseQueue().take());
    } catch (InterruptedException cause) {
      org.eclipse.swt.SWTException e =
          new org.eclipse.swt.SWTException(SWT.ERROR_FAILED_EXEC);
      e.throwable = cause;
      throw e;
    }
  }

  /**
   * Waits for an event.
   * <p>
   * A {@link InterruptedException} is wrapped into a
   * {@link org.eclipse.swt.SWTException}.
   *
   * @param app The source application
   * @return The next event
   * @throws org.eclipse.swt.SWTException if the current thread was interrupted
   */
  private SWTEvent waitForEvent(SWTApplication app)
      throws org.eclipse.swt.SWTException {

    try {
      return (app.getEventQueue().take());
    } catch (InterruptedException cause) {
      org.eclipse.swt.SWTException e =
          new org.eclipse.swt.SWTException(SWT.ERROR_FAILED_EXEC);
      e.throwable = cause;
      throw e;
    }
  }

  /**
   * Normalizes a single argument.
   * <p>
   * If a {@link SWTObject} is passed to the method, the the related
   * {@link SWTObjectId} is returned. Otherwise the argument is simply
   * returned.
   *
   * @param arg The argument to check
   * @return The normalized argument
   */
  private Object normalizeArgument(Object arg) {
    if (arg instanceof SWTObject) {
      return new SWTObjectId(((SWTObject)arg).getId());
    } else {
      return (arg);
    }
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
      result[i] = normalizeArgument(args[i]);
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
