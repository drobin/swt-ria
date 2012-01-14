package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Widget;

/**
 * Classes which implement this interface provide a method that deals with the
 * event that is generated when a widget is disposed.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a widget using the
 * {@link Widget#addDisposeListener(DisposeListener)} method and removed usin
 * the {@link Widget#removeDisposeListener(DisposeListener)} method. When a
 * widget is disposed, the {@link #widgetDisposed(DisposeEvent)} method will
 * be invoked.
 */
public interface DisposeListener extends TypedListener {
  /**
   * Sent when the widget is disposed.
   *
   * @param e an event containing information about the dispose
   */
  void widgetDisposed(DisposeEvent e);
}
