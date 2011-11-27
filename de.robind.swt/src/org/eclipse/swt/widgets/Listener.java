package org.eclipse.swt.widgets;

/**
 * Implementers of Listener provide a simple {@link #handleEvent(Event)} method
 * that is used internally by SWT to dispatch events.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a widget using the addListener(int eventType, Listener handler)
 * method and removed using the removeListener(int eventType, Listener handler)
 * method. When the specified event occurs, handleEvent(...) will be sent to
 * the instance.
 * <p>
 * Classes which implement this interface are described within SWT as providing
 * the <i>untyped listener</i> API. Typically, widgets will also provide a
 * higher-level <i>typed listener</i> API, that is based on the standard
 * {@link java.util.EventListener} pattern.
 * <p>
 * Note that, since all internal SWT event dispatching is based on untyped
 * listeners, it is simple to build subsets of SWT for use on memory
 * constrained, small footprint devices, by removing the classes and methods
 * which implement the typed listener API.
 */
public interface Listener {
  /**
   * Sent when an event that the receiver has registered for occurs.
   *
   * @param event the event which occurred
   */
  void handleEvent(Event event);
}
