package org.eclipse.swt.widgets;

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
  public void handleEvent(Event event) {
    // TODO Needs to be implemented
    throw new UnsupportedOperationException("Not implemented yet");
  }
}