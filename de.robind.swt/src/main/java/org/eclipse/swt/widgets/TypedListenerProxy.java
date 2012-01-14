package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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
      case SWT.DefaultSelection:
        ((SelectionListener)listener).widgetDefaultSelected(new SelectionEvent(e));
        break;
      case SWT.Dispose:
        ((DisposeListener)listener).widgetDisposed(new DisposeEvent(e));
        break;
      case SWT.DragDetect:
        ((DragDetectListener)listener).dragDetected(new DragDetectEvent(e));
        break;
      case SWT.FocusIn:
        ((FocusListener)listener).focusGained(new FocusEvent(e));
        break;
      case SWT.FocusOut:
        ((FocusListener)listener).focusLost((new FocusEvent(e)));
        break;
      case SWT.Help:
        ((HelpListener)listener).helpRequested(new HelpEvent (e));
        break;
      case SWT.KeyDown: {
        KeyEvent event = new KeyEvent(e);
        ((KeyListener)listener).keyPressed(event);
        e.doit = event.doit;
        break;
      }
      case SWT.KeyUp: {
        KeyEvent event = new KeyEvent(e);
        ((KeyListener)listener).keyReleased(event);
        e.doit = event.doit;
        break;
      }
      case SWT.MenuDetect: {
        MenuDetectEvent event = new MenuDetectEvent(e);
        ((MenuDetectListener)listener).menuDetected(event);
        e.x = event.x;
        e.y = event.y;
        e.doit = event.doit;
        break;
      }
      case SWT.MouseDoubleClick:
        ((MouseListener)listener).mouseDoubleClick(new MouseEvent(e));
        break;
      case SWT.MouseEnter:
        ((MouseTrackListener)listener).mouseEnter(new MouseEvent(e));
        break;
      case SWT.MouseExit:
        ((MouseTrackListener)listener).mouseExit(new MouseEvent(e));
        break;
      case SWT.MouseHover:
        ((MouseTrackListener)listener).mouseHover(new MouseEvent(e));
        break;
      case SWT.MouseDown:
        ((MouseListener)listener).mouseDown(new MouseEvent(e));
        break;
      case SWT.MouseMove:
        ((MouseMoveListener)listener).mouseMove(new MouseEvent(e));
        break;
      case SWT.MouseUp:
        ((MouseListener)listener).mouseUp(new MouseEvent(e));
        break;
      case SWT.MouseWheel:
        ((MouseWheelListener)listener).mouseScrolled(new MouseEvent(e));
        return;
      case SWT.Move:
        ((ControlListener)listener).controlMoved(new ControlEvent(e));
        break;
      case SWT.Paint: {
        PaintEvent event = new PaintEvent (e);
        ((PaintListener)listener).paintControl(event);
        e.gc = event.gc;
        break;
      }
      case SWT.Resize:
        ((ControlListener)listener).controlResized(new ControlEvent(e));
        break;
      case SWT.Selection: {
        SelectionEvent event = new SelectionEvent(e);
        ((SelectionListener)listener).widgetSelected(event);
        e.x = event.x;
        e.y = event.y;
        e.doit = event.doit;
        break;
      }
      case SWT.Traverse: {
        TraverseEvent event = new TraverseEvent (e);
        ((TraverseListener)listener).keyTraversed(event);
        e.detail = event.detail;
        e.doit = event.doit;
        break;
      }
      default:
        throw new UnsupportedOperationException(
            "Event-type " + e.type + " not implemented yet");
    }
  }
}
