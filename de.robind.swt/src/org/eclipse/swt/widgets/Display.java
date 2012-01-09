package org.eclipse.swt.widgets;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.SWTObject;
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
   * List contains all created displays.
   */
  private static List<Display> displayList = new LinkedList<Display>();

  /**
   * The assigned key.
   */
  private Key key;

  /**
   * The thread which has created the display
   */
  Thread thread;

  /**
   * Things to do on the user-thread
   */
  private Queue<ToDo> dispatcherQueue = new LinkedList<Display.ToDo>();

  /**
   * Constructs a new instance of this class.
   * <p>
   * Note: The resulting display is marked as the <i>current</i> display. If
   * this is the first display which has been constructed since the application
   * started, it is also marked as the default display.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Display() throws SWTException {
    this(null);
  }

  /**
   * Constructs a new instance of this class using the parameter.
   *
   * @param data the device data
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Display(DeviceData data) throws SWTException {
    super(data);
  }

  /**
   * Causes the {@link Runnable#run()} method of the runnable to be invoked by
   * the user-interface thread at the next reasonable opportunity. The caller
   * of this method continues to run in parallel, and is not notified when the
   * runnable has completed. Specifying <code>null</code> as the runnable
   * simply wakes the user-interface thread when run.
   * <p>
   * Note that at the time the runnable is invoked, widgets that have the
   * receiver as their display may have been disposed. Therefore, it is
   * necessary to check for this case inside the runnable before accessing
   * the widget.
   *
   * @param runnable code to run on the user-interface thread or
   *                 <code>null</code>
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void asyncExec(Runnable runnable) throws SWTException {
    super.checkDevice();

    if (runnable == null) {
      runnable = new Runnable() {
        public void run() {
        }
      };
    }

    ToDo todo = new ToDo(runnable);
    this.dispatcherQueue.offer(todo);
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
    checkDevice();
    // TODO Check for ERROR_FAILED_EXEC
    ToDo todo = null;
    while ((todo = this.dispatcherQueue.poll()) != null) {
      if (todo.event != null) {
        Widget widget = (Widget)SWTObject.findObjectById(todo.event.swtObjectId);
        widget.notifyListeners(todo.event.type, todo.event);
      }

      if (todo.asyncExec != null) {
        todo.asyncExec.run();
      }
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
    checkDevice();

    ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
    try {
      Event event = clientTasks.waitForEvent(key);
      if (event != null) {
        ToDo todo = new ToDo(event);
        this.dispatcherQueue.offer(todo);
        return (true);
      } else {
        return (false);
      }
    } catch (Exception e) {
      // TODO What is the best fitting exception-code
      SWTException exc = new SWTException();
      exc.throwable = e;
      throw exc;
    }
  }

  /**
   * Returns the key assigned to the Display.
   * @return The assigned key
   *
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
  public Key getKey() throws SWTException {
    checkDevice();
    return (this.key);
  }

  /**
   * Assigns a key to the Display-instance.
   *
   * @param key The key to be assigned
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if <code>key</code> is <code>null</code>
   *    </li>
   *  </ul>
   */
  public void setKey(Key key) throws SWTException {
    if (key == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    synchronized (Device.class) {
      this.key = key;
    }
  }

  /**
   * Returns the display which the given thread is the user-interface thread
   * for, or <code>null</code> if the given thread is not a user-interface
   * thread for any display. Specifying <code>null</code> as the thread will
   * return <code>null</code> for the display.
   *
   * @param thread the user-interface thread
   * @return the display for the given thread
   */
  public static Display findDisplay(Thread thread) {
    synchronized (Device.class) {
      for (Display display: Display.displayList) {
        if (display.thread == thread) {
          return (display);
        }
      }

      return (null);
    }
  }

  /**
   * Returns the display which the currently running thread is the
   * user-interface thread for, or <code>null</code> if the currently running
   * thread is not a user-interface thread for any display.
   *
   * @return the current display
   */
  public static Display getCurrent() {
    return (Display.findDisplay(Thread.currentThread()));
  }

  public void createObject(int id, Class<?> objClass, Object... args)
      throws SWTException {

    checkDevice();

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

  public Object callMethod(int id, String method, Object... args)
      throws SWTException {

    checkDevice();

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

  public void registerEvent(int id, int eventType, boolean enable)
      throws SWTException {

    checkDevice();

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

  public void updateAttribute(SWTObject obj, String attrName)
      throws SWTException {

    checkDevice();

    try {
      Object attrValue = obj.getClass().getField(attrName).get(obj);
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();

      clientTasks.updateAttribute(getKey(), obj.getId(), attrName, attrValue);
    } catch (Throwable t) {
      // TODO Do you need a special code for the exception?
      SWTException e = new SWTException();
      e.throwable = (t instanceof InvocationTargetException) ?
          ((InvocationTargetException)t).getCause() : t;
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.graphics.Device#checkDevice()
   */
  @Override
  protected void checkDevice() throws SWTException {
    super.checkDevice();

    if (this.thread == null) {
      throw new SWTException(SWT.ERROR_WIDGET_DISPOSED);
    }

    if (this.thread != Thread.currentThread ()) {
      throw new SWTException(SWT.ERROR_THREAD_INVALID_ACCESS);
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.graphics.Device#create(org.eclipse.swt.graphics.DeviceData)
   */
  @Override
  protected void create(DeviceData data) {
    DisplayPool.getInstance().addDisplay(this);
    this.thread = Thread.currentThread();
    Display.displayList.add(this);

    try {
      ClientTasks clientTasks = DisplayPool.getInstance().getClientTasks();
      clientTasks.createObject(getKey(), getId(), getClass());
    } catch (Throwable t) {
      // TODO What should you do with the exception?
      throw new Error(t);
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.graphics.Device#init()
   */
  @Override
  protected void init() {
    super.init();
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.graphics.Device#release()
   */
  @Override
  protected void release() {
    super.release();
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.graphics.Device#destroy()
   */
  @Override
  protected void destroy() {
    synchronized (Device.class) {
      Display.displayList.remove(this);
    }
  }

  /**
   * Something to do next on the user-thread.
   * @see #readAndDispatch()
   */
  private static class ToDo {
    Event event = null;
    Runnable asyncExec = null;

    ToDo(Event event) {
      this.event = event;
    }

    ToDo(Runnable async) {
      this.asyncExec = async;
    }
  }
}
