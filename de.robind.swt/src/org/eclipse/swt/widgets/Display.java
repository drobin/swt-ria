package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.server.ClientTasks;
import org.eclipse.swt.server.DisplayPool;
import org.eclipse.swt.server.Key;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

/**
 * TODO Needs to be implemented!!
 */
public class Display extends Device {
  /**
   * Factory used to create {@link SWTMessage}-instances.
   */
  private SWTMessageFactory factory = new SWTMessageFactory();

  /**
   * The assigned key.
   */
  private Key key = null;

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

    // TODO Evaluate the response
    SWTNewRequest request = new SWTNewRequest(getId().getId(), Display.class);
    sendMessage(request);
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
    return (true);
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
    return (false);
  }

  public Key getKey() {
    return (this.key);
  }

  public void setKey(Key key) {
    this.key = key;
  }

  /**
   * Returns the factory used to create {@link SWTMessage}-instances.
   *
   * @return The {@link SWTMessage}-factory
   */
  SWTMessageFactory getMessageFactory() {
    return (this.factory);
  }

  /**
   * Sends a request-message to the client.
   * <p>
   * The method blocks until the response arrived.
   *
   * @param request The message to be send
   * @return The answer send back from the client
   */
  SWTResponse sendMessage(SWTRequest request) {
    ClientTasks tasks = DisplayPool.getInstance().getClientTasks();
    return (tasks.sendRequest(getKey(), request));
  }
}
