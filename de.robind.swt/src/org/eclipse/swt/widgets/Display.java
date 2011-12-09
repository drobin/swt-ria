package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;

/**
 * TODO Needs to be implemented!!
 */
public class Display extends Device {
  /**
   * The assigned key.
   */
  private Key key = null;

  /**
   * Event received from the client.
   * Needs to be handled.
   */
  private Event nextEvent = null;

  /**
   * TODO Needs to be implemented!!
   */
  public Display() {
    this(null);
  }

  /**
   * TODO Needs to be implemented!!
   */
  public Display(DeviceData data) {
    DisplayPool.getInstance().addDisplay(this);

    try {
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.createObject(getKey(), getId(), getClass());
    } catch (Throwable t) {
      // TODO What should you do with the exception?
      throw new Error(t);
    }
  }

  /**
   * Reads an event from the operating system's event queue, dispatches it
   * appropriately, and returns <code>true</code> if there is potentially more
   * work to do, or <code>false</code> if the caller can sleep until another
   * event is placed on the event queue.
   * <p>
   * In addition to checking the system event queue, this method also checks if
   * any inter-thread messages (created by {@link #syncExec()} or
   * {@link #asyncExec()}) are waiting to be processed, and if so handles them
   * before returning.
   *
   * @return <code>false</code> if the caller can sleep upon return from this
   *         method.
   * @throws SWTException
   *
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_FAILED_EXEC} -
   *      if an exception occurred while running an inter-thread message
   *    </li>
   *  </ul>
   */
  public boolean readAndDispatch() throws SWTException {
    if (this.nextEvent != null) {
      Widget widget = this.nextEvent.widget;
      widget.notifyListeners(this.nextEvent.type, this.nextEvent);

      this.nextEvent = null;
    }

    return (false);
  }

  /**
   * Causes the user-interface thread to <i>sleep</i> (that is, to be put in a
   * state where it does not consume CPU cycles) until an event is received or
   * it is otherwise awakened.
   *
   * @return <code>true</code> if an event requiring dispatching was placed on
   *         the queue.
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public boolean sleep() throws SWTException {
    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS
    ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
    try {
      this.nextEvent = clientTasks.waitForEvent(key);
      return (this.nextEvent != null);
    } catch (Exception e) {
      // TODO What is the best fitting exception-code
      SWTException exc = new SWTException();
      exc.throwable = e;
      throw exc;
    }
  }

  public Key getKey() {
    return (this.key);
  }

  public void setKey(Key key) {
    this.key = key;
  }

  void createObject(int id, Class<?> objClass, Object... args)
      throws SWTException {

    try {
      ClientTasks tasks = DisplayPool.getInstance().getClientTasks();
      tasks.createObject(getKey(), id, objClass, args);
    } catch (Throwable t) {
      // TODO Do you need a special code for the exception?
      SWTException e = new SWTException();
      e.throwable = t;
      throw e;
    }
  }

  Object callMethod(int id, String method, Object... args)
      throws SWTException {

    try {
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      return (clientTasks.callMethod(getKey(), id, method, args));
    } catch (Throwable t) {
      // TODO Do you need a special code for the exception?
      SWTException e = new SWTException();
      e.throwable = t;
      throw e;
    }
  }

  void registerEvent(int id, int eventType, boolean enable)
      throws SWTException {

    try {
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.registerEvent(getKey(), id, eventType, enable);
    } catch (Throwable t) {
      // TODO Do you need a special code for the exception?
      SWTException e = new SWTException();
      e.throwable = t;
      throw e;
    }
  }
}
