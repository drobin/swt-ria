package org.eclipse.swt.widgets;

import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.TypedListener;
import org.eclipse.swt.server.ClientTasks;

/**
 * This class is the abstract superclass of all user interface objects.
 * Widgets are created, disposed and issue notification to listeners when
 * events occur which affect them.
 *
 * <dl>
 *  <dt><b>Styles:</b></dt>
 *  <dd>(none)</dd>
 *  <dt><b>Events:</b></dt>
 *  <dd>Dispose</dd>
 * </dl>
 *
 * IMPORTANT: This class is intended to be subclassed <i>only</i> within the
 * SWT implementation. However, it has not been marked final to allow those
 * outside of the SWT development team to implement patched versions of the
 * class in order to get around specific limitations in advance of when those
 * limitations can be addressed by the team. Any class built using subclassing
 * to access the internals of this class will likely fail to compile or run
 * between releases and may be strongly platform specific. Subclassing should
 * not be attempted without an intimate and detailed understanding of the
 * workings of the hierarchy. No support is provided for user-written classes
 * which are implemented as subclasses of this class.
 */
public abstract class Widget extends SWTObject {
  /**
   * The parent widget.
   */
  private Widget parent = null;

  /**
   * The style-flags of the widget
   */
  private int style = 0;

  /**
   * Data assigned to the widget
   */
  private Object data = null;

  /**
   * Dispose-state of the widget
   */
  private boolean disposed = false;

  /**
   * Properties assigned to the widget
   */
  private Properties dataProperties = null;

  /**
   * Table stores all assigned listener.
   */
  private ListenerTable listenerTable = new ListenerTable(this);

  /**
   * Constructs a new instance of this class given its parent and a style value
   * describing its behavior and appearance.
   * <p>
   * The style value is either one of the style constants defined in class
   * {@link SWT} which is applicable to instances of this class, or must be
   * built by bitwise OR'ing together (that is, using the int "|" operator)
   * two or more of those {@link SWT} style constants. The class description
   * lists the style constants that are applicable to the class. Style bits are
   * also inherited from superclasses.
   *
   * @param parent a widget which will be the parent of the new instance
   *                (cannot be null)
   * @param style the style of widget to construct
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the parent is null
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_ARGUMENT} -
   *      if the parent is disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the parent
   *    </li>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  public Widget(Widget parent, int style) throws SWTException {
    if (parent == null && getClass() != Shell.class) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    if (parent != null && parent.isDisposed()) {
      throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
    }

    checkSubclass();

    this.parent = parent;
    this.style = style;

    Display display = getDisplay();
    if (display != null) {
      checkThread();
      display.createObject(getId(), getCreateClass(), this.parent, this.style);
    }
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * an event of the given type occurs. When the event does occur in the widget,
   * the listener is notified by sending it the handleEvent() message. The event
   * type is one of the event constants defined in class SWT.
   *
   * @param eventType the type of event to listen for
   * @param listener the listener which should be notified when the event occurs
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void addListener(int eventType, Listener listener) throws SWTException {
    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    this.listenerTable.addListener(eventType, listener);
  }

  /**
   * Adds the listener to the collection of listeners who will be notified when
   * the widget is disposed. When the widget is disposed, the listener is
   * notified by sending it the
   * {@link DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)}
   * message.
   *
   * @param listener the listener which should be notified when the receiver
   *                 is disposed
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void addDisposeListener(DisposeListener listener)
      throws SWTException {

    addTypedListener(listener, SWT.Dispose);
  }

  /**
   * Adds the listener-proxy to the collection of listeners who will be
   * notified when an event of the given types occurs.
   *
   * @param listener the listener which should be notified when the events
   *                 occur
   * @param eventTypes Event-typed, where the proxy should be registered
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  protected void addTypedListener(TypedListener listener, int... eventTypes)
      throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();
    TypedListenerProxy proxy = new TypedListenerProxy(listener);

    for (int eventType: eventTypes) {
      addListener(eventType, proxy);
    }
  }

  private void checkThread() throws SWTException {
    if (getDisplay().thread != Thread.currentThread ()) {
      throw new SWTException(SWT.ERROR_THREAD_INVALID_ACCESS);
    }
  }

  private void checkDisposed() throws SWTException {
    if (getDisplay() == null || isDisposed()) {
      throw new SWTException(SWT.ERROR_WIDGET_DISPOSED);
    }
  }

  /**
   * Checks that this class can be subclassed.
   * <p>
   * The SWT class library is intended to be subclassed only at specific,
   * controlled points (most notably, Composite and Canvas when implementing
   * new widgets). This method enforces this rule unless it is overridden.
   * <p>
   * <i>IMPORTANT:</i> By providing an implementation of this method that
   * allows a subclass of a class which does not normally allow subclassing to
   * be created, the implementer agrees to be fully responsible for the fact
   * that any such subclass will likely fail between SWT releases and will be
   * strongly platform specific. No support is provided for user-written
   * classes which are implemented in this fashion.
   * <p>
   * The ability to subclass outside of the allowed SWT classes is intended
   * purely to enable those not on the SWT development team to implement
   * patches in order to get around specific limitations in advance of when
   * those limitations can be addressed by the team. Subclassing should not be
   * attempted without an intimate and detailed understanding of the hierarchy.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_INVALID_SUBCLASS} -
   *      if this class is not an allowed subclass
   *    </li>
   *  </ul>
   */
  protected void checkSubclass() throws SWTException {
    // The class needs to be located inside the "org.eclipse.swt"-package
    String name = getClass().getPackage().getName();
    if (!name.startsWith("org.eclipse.swt")) {
      throw new SWTException(SWT.ERROR_INVALID_SUBCLASS);
    }
  }

  /**
   * Throws an {@link SWTException} if the receiver can not be accessed by the
   * caller. This may include both checks on the state of the receiver and more
   * generally on the entire execution context. This method <i>should</i> be
   * called by widget implementors to enforce the standard SWT invariants.
   * <p>
   * Currently, it is an error to invoke any method (other than
   * {@link #isDisposed()}) on a widget that has had its dispose() method
   * called. It is also an error to call widget methods from any thread that is
   * different from the thread that created the widget.
   * <p>
   * In future releases of SWT, there may be more or fewer error checks and
   * exceptions may be thrown for different reasons.
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
  protected void checkWidget() throws SWTException {
    checkDisposed();
    checkThread();
  }

  /**
   * Disposes of the operating system resources associated with the receiver
   * and all its descendants. After this method has been invoked, the receiver
   * and all descendants will answer <code>true</code> when sent the message
   * {@link #isDisposed()}. Any internal connections between the widgets in the
   * tree will have been removed to facilitate garbage collection. This method
   * does nothing if the widget is already disposed.
   * <p>
   * NOTE: This method is not called recursively on the descendants of the
   * receiver. This means that, widget implementers can not detect when a
   * widget is being disposed of by re-implementing this method, but should
   * instead listen for the Dispose event.
   *
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void dispose() throws SWTException {
    checkThread();
    getDisplay().callMethod(getId(), "dispose");
    this.disposed = true;
  }

  /**
   * Returns the class which is passed the the
   * {@link ClientTasks#createObject(org.eclipse.swt.server.Key, int, Class, Object...) object-creation-request}.
   * <p>
   * Normally is should be {@link #getClass() this class}, but if you create
   * your own widgets (derivated from {@link Composite}), this (new) class is
   * not known by the client. So it needs to be transformed into a class from
   * the SWT-framework. This methes is responsible for that.
   * <p>
   * Classes, which can be subclassed, should overwrite this method and return
   * the correct class.
   *
   * @return The class, which is used for the create-request send to the
   *         client.
   */
  protected Class<? extends Widget> getCreateClass() {
    return (getClass());
  }

  /**
   * Returns the application defined widget data associated with the receiver,
   * or <code>null</code> if it has not been set. The widget data is a single,
   * unnamed field that is stored with every widget.
   * <p>
   * Applications may put arbitrary objects in this field. If the object stored
   * in the widget data needs to be notified when the widget is disposed of, it
   * is the application's responsibility to hook the Dispose event on the
   * widget and do so.
   *
   * @return the widget data
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
  public Object getData() throws SWTException {
    checkWidget();
    return (this.data);
  }

  /**
   * Returns the application defined property of the receiver with the
   * pecified name, or <code>null</code> if it has not been set.
   * <p>
   * Applications may have associated arbitrary objects with the receiver in
   * this fashion. If the objects stored in the properties need to be notified
   * when the widget is disposed of, it is the application's responsibility to
   * hook the Dispose event on the widget and do so.
   *
   * @param key the name of the property
   * @return the value of the property or <code>null</code> if it has not been
   *         set
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the key is <code>null</code>
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public Object getData(String key) throws SWTException {
    if (key == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    if (this.dataProperties == null) {
      return (null);
    }

    return (this.dataProperties.get(key));
  }

  /**
   * Returns the {@link Display} that is associated with the receiver.
   * <p>
   * A widget's display is either provided when it is created (for example, top
   * level {@link Shell}s) or is the same as its parent's display.
   *
   * @return the receiver's display
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public Display getDisplay() throws SWTException {
    if (isDisposed()) {
      throw new SWTException(SWT.ERROR_WIDGET_DISPOSED);
    }
    return (this.parent.getDisplay());
  }

  /**
   * Returns an array of listeners who will be notified when an event of the
   * given type occurs.
   * The event type is one of the event constants defined in class
   * <code>SWT</code>.
   *
   * @param eventType the type of event to listen for
   * @return an array of listeners that will be notified when the event occurs
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public Listener[] getListeners(int eventType) throws SWTException {
    checkWidget();
    return (this.listenerTable.getListener(eventType));
  }

  /**
   * Returns the parent (if any) of this widget.
   * <p>
   * NOTE: This method not not part of the official SWT-API.
   *
   * @return The parent widget. If this widget is the top of the object tree,
   *         <code>null</code> is returned.
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public Widget getParent() throws SWTException {
    checkWidget();
    return (this.parent);
  }

  /**
   * Returns the receiver's style information.
   * <p>
   * Note that the value which is returned by this method may not match the
   * value which was provided to the constructor when the receiver was created.
   * This can occur when the underlying operating system does not support a
   * particular combination of requested styles. For example, if the platform
   * widget used to implement a particular SWT widget always has scroll bars,
   * the result of calling this method would always have the SWT.H_SCROLL and
   * SWT.V_SCROLL bits set.
   *
   * @return the style bits
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
  public int getStyle() throws SWTException {
    checkWidget();
    return (this.style);
  }

  /**
   * Returns <code>true</code> if the widget has been disposed, and
   * <code>false</code> otherwise.
   * <p>
   * This method gets the dispose state for the widget. When a widget has been
   * disposed, it is an error to invoke any other method
   * (except {@link #dispose()}) using the widget.
   *
   * @return <code>true</code> when the widget is disposed and
   *         <code>false</code> otherwise.
   */
  public boolean isDisposed() {
    return (this.disposed);
  }

  /**
   * Returns <code>true</code> if there are any listeners for the specified
   * event type associated with the receiver, and <code>false</code> otherwise.
   * The event type is one of the event constants defined in class SWT.
   *
   * @param eventType the type of event
   * @return true if the event is hooked
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public boolean isListening(int eventType) throws SWTException {
    checkWidget();
    return (this.listenerTable.haveListener(eventType));
  }

  /**
   * Notifies all of the receiver's listeners for events of the given type that
   * one such event has occurred by invoking their
   * {@link Listener#handleEvent(Event)} method.
   * The event type is one of the event constants defined in class
   * <code>SWT</code>.
   *
   * @param eventType the type of event which has occurred
   * @param event the event data
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void notifyListeners(int eventType, Event event) throws SWTException {
    checkWidget();

    if (event == null) {
      event = new Event();
    }

    event.type = eventType;
    event.display = getDisplay();
    event.widget = this;
    event.time = 0; // TODO Fix time handling

    for (Listener listener: getListeners(eventType)) {
      listener.handleEvent(event);
    }
  }

  /**
   * Removes the listener from the collection of listeners who will be notified
   * when an event of the given type occurs. The event type is one of the event
   * constants defined in class SWT.
   *
   * @param eventType the type of event to listen for
   * @param listener the listener which should no longer be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void removeListener(int eventType, Listener listener)
      throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    this.listenerTable.removeListener(eventType, listener);
  }

  /**
   * Removes the listener from the collection of listeners who will be
   * notified when the widget is disposed.
   *
   * @param listener the listener which should no longer be notified
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the listener is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void removeDisposeListener(DisposeListener listener)
      throws SWTException {

    removeTypedListener(listener, SWT.Dispose);
  }

  /**
   * Removes the listener from the collection of listeners who will be
   * notified when the widget is disposed.
   *
   * @param listener the listener which should no longer be notified
   * @param eventTypes Event-types, where the proxy shoudl be unregistered
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the proxy is null
   *    </li>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  protected void removeTypedListener(TypedListener listener,
      int... eventTypes) throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    for (int eventType: eventTypes) {
      this.listenerTable.removeTypedListener(eventType, listener);
    }
  }

  /**
   * Marks the widget to be skinned.
   * <p>
   * The skin event is sent to the receiver's display when appropriate
   * (usually before the next event is handled). Widgets are automatically
   * marked for skinning upon creation as well as when its skin id or class
   * changes. The skin id and/or class can be changed by calling
   * {@link Display#setData(String, Object)} with the keys {@link SWT#SKIN_ID}
   * and/or {@link SWT#SKIN_CLASS}. Once the skin event is sent to a widget,
   * it will not be sent again unless {@link #reskin(int)} is called on the
   * widget or on an ancestor while specifying the {@link SWT#ALL} flag.
   *
   * @param flags the flags specifying how to reskin
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void reskin(int flags) throws SWTException {
    checkWidget();
    getDisplay().callMethod(getId(), "reskin", flags);
  }

  /**
   * Sets the application defined widget data associated with the receiver to
   * be the argument. The widget data is a single, unnamed field that is stored
   * with every widget.
   * <p>
   * Applications may put arbitrary objects in this field. If the object stored
   * in the widget data needs to be notified when the widget is disposed of,
   * it is the application's responsibility to hook the Dispose event on the
   * widget and do so.
   *
   * @param data the widget data
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_WIDGET_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *  </ul>
   */
  public void setData(Object data) throws SWTException {
    checkWidget();
    this.data = data;
  }

  /**
   * Sets the application defined property of the receiver with the specified
   * name to the given value.
   * <p>
   * Applications may associate arbitrary objects with the receiver in this
   * fashion. If the objects stored in the properties need to be notified when
   * the widget is disposed of, it is the application's responsibility to hook
   * the Dispose event on the widget and do so.
   *
   * @param key the name of the property
   * @param value the new value for the property
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_NULL_ARGUMENT} -
   *      if the key is <code>null</code>
   *    </li>
   *    <li>{@link SWT#ERROR_THREAD_INVALID_ACCESS} -
   *      if not called from the thread that created the receiver
   *    </li>
   *    <li>{@link SWT#ERROR_DEVICE_DISPOSED} -
   *      if the receiver has been disposed
   *    </li>
   *  </ul>
   */
  public void setData(String key, Object value) throws SWTException {
    if (key == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    checkWidget();

    if (this.dataProperties == null) {
      this.dataProperties = new Properties();
    }

    if (value != null) {
      this.dataProperties.put(key, value);
    } else {
      this.dataProperties.remove(key);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + "[objId=" + this.getId() + "]";
  }
}
