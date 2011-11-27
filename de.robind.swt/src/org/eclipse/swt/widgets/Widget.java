package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.events.TypedListener;

import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTObjectId;

/**
 * TODO Needs to be implemented!!
 */
public class Widget extends SWTObject {
  /**
   * The top-level display
   */
  private Display display = null;

  /**
   * The parent widget.
   */
  private Widget parent = null;

  /**
   * The style-flags of the widget
   */
  private int style = 0;

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
    if (canHaveParent() && parent == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    if (parent != null && parent.canDispose() && parent.isDisposed()) {
      throw new SWTException(SWT.ERROR_INVALID_ARGUMENT);
    }

    // Will throw SWTException (with SWT.ERROR_INVALID_SUBCLASS) on failure
    checkSubclass();

    // TODO Check for SWT.ERROR_THREAD_INVALID_ACCESS

    this.parent = parent;
    this.style = style;
    setDisplay((parent != null) ? parent.display : null);
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

    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS

    this.listenerTable.addListener(eventType, listener);
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

    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS

    this.listenerTable.removeListener(eventType, listener);
  }

  /**
   * Removes a {@link TypedListener} from the collection of listeners.
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
  void removeTypedListener(int eventType, TypedListener listener)
      throws SWTException {

    if (listener == null) {
      throw new SWTException(SWT.ERROR_NULL_ARGUMENT);
    }

    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS

    this.listenerTable.removeListener(eventType, listener);
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
    // TODO Check for ERROR_WIDGET_DISPOSED, ERROR_THREAD_INVALID_ACCESS

    return (this.listenerTable.haveListener(eventType));
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
   *
   * TODO Needs to be implemented!!
   */
  public boolean isDisposed() {
    return (false);
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
    if (canDispose() && isDisposed()) {
      throw new SWTException(SWT.ERROR_WIDGET_DISPOSED);
    }

    return (this.display);
  }

  /**
   * Assigns a {@link Display} to the widget.
   * <p>
   * If assigned, you can also ask the display for a
   * {@link Display#generateId() unique id}. Finally a message is generated,
   * that the widget can be created on client-side.
   *
   * @param display The display to be assign
   * @throws SWTException
   *  <ul>
   *    <li>{@link SWT#ERROR_INVALID_MESSAGE} -
   *      if an invalid protocol-message was generated
   *    </li>
   *  </ul>
   */
  void setDisplay(Display display) throws SWTException {
    this.display = display;

    if (display != null) {
      display.sendMessage(createNewRequest());
    }
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
    // TODO Check for ERROR_THREAD_INVALID_ACCESS, ERROR_DEVICE_DISPOSED
    return (this.style);
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
  }

  /**
   * Creates a new {@link SWTNewRequest}-message.
   * <p>
   * This method is called by {@link #setDisplay(Display)} to send the
   * creation-request to the client. You can overwrite the method if you need
   * another request-message. For example, you you need other arguments passed
   * to the constructor.
   *
   * @return A new {@link SWTNewRequest}, which creates a new instance of the
   *         class.
   */
  protected SWTNewRequest createNewRequest() {
    SWTObjectId parentId =
        (this.parent != null) ? this.parent.getId() : SWTObjectId.undefined();
    return (new SWTNewRequest(getId().getId(), getClass(), parentId, this.style));
  }

  /**
   * Checks whether this widget can have a parent.
   * <p>
   * If this method returns <code>true</code>, the constructor will check for
   * a <code>null</code>-parent and raise an {@link SWTException}. If this
   * method returns <code>false</code>, the check is not performed.
   *
   * @return <code>true</code> is returned, if this widget can have a parent.
   */
  boolean canHaveParent() {
    return (true);
  }

  /**
   * Checks whether this widget can be disposed.
   * <p>
   * The widget provides an {@link #isDisposed() disposed-state}. The
   * constructor will check the disposed-state of the parent widget and raise
   * a {@link SWTException}, if already disposed. Some widgets cannot have a
   * disposed-state, if this method returns <code>false</code>, the check is
   * not performed.
   *
   * @return <code>true</code> is returned, if the method can be disposed.
   */
  boolean canDispose() {
    return (true);
  }
}
