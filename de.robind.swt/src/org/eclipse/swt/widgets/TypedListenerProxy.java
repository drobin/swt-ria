package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedListener;

/**
 * Instances of this class are <i>internal SWT implementation</i> objects which
 * provide a mapping between the typed and untyped listener mechanisms that
 * SWT supports.
 * <p>
 * <b>IMPORTANT:</b> This class is <i>not</i> part of the SWT public API. It is
 * marked public only so that it can be shared within the packages provided by
 * SWT. It should never be referenced from application code.
 */
public class TypedListenerProxy implements Listener {
  /**
   * The encapsulated {@link TypedListener}.
   */
  private TypedListener listener = null;

  public TypedListenerProxy(TypedListener listener) {
    this.listener = listener;
  }

  /**
   * Returns the assigned listener.
   *
   * @return The assigned listener
   */
  TypedListener getListener() {
    return (this.listener);
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
   */
  public void handleEvent(Event e) {
    switch (e.type) {
      case SWT.Dispose:
        ((DisposeListener)listener).widgetDisposed(new DisposeEvent(e));
        break;
      case SWT.MouseDoubleClick:
        ((MouseListener)listener).mouseDoubleClick(new MouseEvent(e));
        break;
      case SWT.MouseDown:
        ((MouseListener)listener).mouseDown(new MouseEvent(e));
        break;
      case SWT.MouseUp:
        ((MouseListener)listener).mouseUp(new MouseEvent(e));
        break;
      case SWT.Move:
        ((ControlListener)listener).controlMoved(new ControlEvent(e));
        break;
      case SWT.Resize:
        ((ControlListener)listener).controlResized(new ControlEvent(e));
        break;
      case SWT.Selection:
        SelectionEvent event = new SelectionEvent(e);
        ((SelectionListener)listener).widgetSelected(event);
        e.x = event.x;
        e.y = event.y;
        e.doit = event.doit;
        break;
      default:
        throw new UnsupportedOperationException(
            "Event-type " + e.type + " not implemented yet");
    }
  }
}
